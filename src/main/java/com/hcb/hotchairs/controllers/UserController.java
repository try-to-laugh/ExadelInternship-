package com.hcb.hotchairs.controllers;

import com.hcb.hotchairs.dtos.UserDTO;
import com.hcb.hotchairs.services.IUserService;
import com.hcb.hotchairs.mas.UserDTOModelAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Link NOT_HR = new Link("none", "Staff Bookings");
    private static final Link IS_HR = new Link("here", "Staff Bookings");
    private static final Link NOT_OFFICE_MANAGER = new Link("none", "Manage Offices");
    private static final Link IS_OFFICE_MANAGER = new Link("here", "Manage Offices");
    private static final Link NOT_ROLE_MANAGER = new Link("none", "Manage Roles");
    private static final Link IS_ROLE_MANAGER = new Link("here", "Manage Roles");

    private final IUserService userService;
    private final UserDTOModelAssembler assembler;

    @Autowired
    public UserController(IUserService userService, UserDTOModelAssembler assembler) {
        this.userService = userService;
        this.assembler = assembler;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<UserDTO>> getById(@PathVariable("id") Long id) {

        EntityModel<UserDTO> entityModel;
        UserDTO userDTO = userService.getById(id);

        if (userDTO.is("All")) {
            entityModel = assembler.toModel(userDTO,
                    IS_HR, IS_OFFICE_MANAGER, IS_ROLE_MANAGER);
        } else if (userDTO.is("HR")) {
            entityModel = assembler.toModel(userDTO,
                    IS_HR, NOT_OFFICE_MANAGER, NOT_ROLE_MANAGER);
        } else if (userDTO.is("Office Manager")) {
            entityModel = assembler.toModel(userDTO,
                    NOT_HR, IS_OFFICE_MANAGER, NOT_ROLE_MANAGER);
        } else if (userDTO.is("Admin")) {
            entityModel = assembler.toModel(userDTO,
                    IS_HR, IS_OFFICE_MANAGER, IS_ROLE_MANAGER);
        } else {
            entityModel = assembler.toModel(userDTO,
                    NOT_HR, NOT_OFFICE_MANAGER, NOT_ROLE_MANAGER);
        }

        /**TODO
         * 1. clarify about the roles, whether the user can have the HR and Office Manager roles,
         * and, if so, implement additional checks
         */

        return ResponseEntity.ok(entityModel);
    }

    @GetMapping("")
    public ResponseEntity<List<UserDTO>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }
}
