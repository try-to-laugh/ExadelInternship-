package com.hcb.hotchairs.services.impl;

import com.hcb.hotchairs.daos.IUserDAO;
import com.hcb.hotchairs.entities.User;
import com.hcb.hotchairs.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    private final IUserDAO userDAO;

    @Autowired
    public UserService(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public User getById(Long id) {
        return userDAO.findById(id).orElse(null);
    }

    @Override
    public List<User> getAll() {
        return userDAO.findAll();
    }
}
