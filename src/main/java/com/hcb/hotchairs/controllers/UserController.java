package com.hcb.hotchairs.controllers;

import com.hcb.hotchairs.converters.UserConverter;
import com.hcb.hotchairs.dtos.UserDTO;
import com.hcb.hotchairs.entities.User;
import com.hcb.hotchairs.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final IUserService userService;
    private final UserConverter userConverter;

    @Autowired
    public UserController(IUserService userService, UserConverter userConverter) {
        this.userService = userService;
        this.userConverter = userConverter;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable("id") Long id) {
        User user = userService.getById(id);
        return Objects.isNull(user)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(userConverter.toDTO(user));
    }

    @GetMapping("/")
    public List<UserDTO> getAll() {
        return userService.getAll().stream().map(userConverter::toDTO).collect(Collectors.toList());
    }
}
