package com.hcb.hotchairs.configs;

import com.hcb.hotchairs.services.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final ILoginService loginService;

    private final RESTAuthenticationEntryPoint entryPoint;
    private final RESTAuthenticationFailureHandler failureHandler;
    private final RESTAuthenticationSuccessHandler successHandler;

    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder,
                          ILoginService loginService,
                          RESTAuthenticationEntryPoint entryPoint,
                          RESTAuthenticationFailureHandler failureHandler,
                          RESTAuthenticationSuccessHandler successHandler) {
        this.passwordEncoder = passwordEncoder;
        this.loginService = loginService;
        this.entryPoint = entryPoint;
        this.failureHandler = failureHandler;
        this.successHandler = successHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                    .disable()
                .requiresChannel()
                   .anyRequest()
                    .requiresSecure()
                .and()
                .authorizeRequests()
                    .antMatchers("/v2/api-docs",
                    "/configuration/ui",
                    "/swagger-resources/**",
                    "/configuration/security",
                    "/swagger-ui.html",
                    "/webjars/**").permitAll()
                    .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    .antMatchers("/api/login", "/api/logout").permitAll()
                    .antMatchers("/api/**").authenticated()
                    .antMatchers("/**").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .exceptionHandling()
                    .authenticationEntryPoint(entryPoint)
                .and()
                .formLogin()
                    .successHandler(successHandler)
                    .failureHandler(failureHandler)
                    .loginProcessingUrl("/api/login")
                    .permitAll()
                .and()
                .rememberMe()
                    .key("Cv2XaEtT66YHtdNhJuUn")
                    .userDetailsService(loginService)
                .and()
                .logout()
                    .logoutUrl("/api/logout")
                    .logoutSuccessHandler((httpServletRequest, httpServletResponse, authentication) ->
                            httpServletResponse.setStatus(HttpServletResponse.SC_OK))
                    .deleteCookies("JSESSIONID")
                    .invalidateHttpSession(true)
                    .permitAll();

        /* TODO:
            1. Disable Test API before release.
            2. Recheck RememberMe options (Remember time, request <remember-me> parameter name, etc.).
            3. Enable CSRF.
            4. DISABLE /** PERMIT ALL!!!
         */
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();

        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Collections.singletonList("*"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(loginService);
        return provider;
    }

}