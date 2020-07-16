package com.hcb.hotchairs.configs;

import com.hcb.hotchairs.services.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final ILoginService loginService;

    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder,
                                     ILoginService loginService) {
        this.passwordEncoder = passwordEncoder;
        this.loginService = loginService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/test/**").permitAll()
                    .anyRequest().authenticated()
                .and()
                .formLogin()
                    .permitAll()
                    .defaultSuccessUrl("/main", true)
                .and()
                    .rememberMe()
                    .key("Cv2XaEtT66YHtdNhJuUn")
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login");

        /* TODO:
            1. Disable Test API before release.
            2. Recheck RememberMe options (Remember time, request <remember-me> parameter name, etc.).
            3. DefaultSuccessfulUrl() second parameter, maybe we don't need force redirection to /index.
         */
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