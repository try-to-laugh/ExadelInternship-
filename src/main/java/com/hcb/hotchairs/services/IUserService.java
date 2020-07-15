package com.hcb.hotchairs.services;

import com.hcb.hotchairs.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IUserService {

    User getById(Long id);
    List<User> getAll();
}
