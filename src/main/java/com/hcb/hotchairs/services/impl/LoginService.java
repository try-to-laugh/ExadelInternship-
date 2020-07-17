package com.hcb.hotchairs.services.impl;

import com.hcb.hotchairs.converters.LoginInfoConverter;
import com.hcb.hotchairs.daos.ILoginDAO;
import com.hcb.hotchairs.services.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class LoginService implements ILoginService {

    private final ILoginDAO loginDAO;
    private final LoginInfoConverter loginInfoConverter;

    @Autowired
    public LoginService(ILoginDAO loginDAO, LoginInfoConverter loginInfoConverter) {
        this.loginDAO = loginDAO;
        this.loginInfoConverter = loginInfoConverter;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loginInfoConverter.toDTO(loginDAO.findByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("User with email %s not found.", username))));
    }
}
