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

    private static final Link CAN_NOT_BOOK_FOR_EMPLOYEES = new Link("none", "Staff Bookings");
    private static final Link CAN_BOOK_FOR_EMPLOYEES = new Link("here", "Staff Bookings");
    private static final Link CAN_NOT_MANAGE_OFFICES = new Link("none", "Manage Offices");
    private static final Link CAN_MANAGE_OFFICES = new Link("here", "Manage Offices");
    private static final Link CAN_NOT_MANAGE_ROLES = new Link("none", "Manage Roles");
    private static final Link CAN_MANAGE_ROLES = new Link("here", "Manage Roles");

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

        if (userDTO.is("Admin")) {
            entityModel = assembler.toModel(userDTO,
                    CAN_BOOK_FOR_EMPLOYEES, CAN_MANAGE_OFFICES, CAN_MANAGE_ROLES);
        } else if (userDTO.is("Office Manager")) {
            entityModel = assembler.toModel(userDTO,
                    CAN_NOT_BOOK_FOR_EMPLOYEES, CAN_MANAGE_OFFICES, CAN_NOT_MANAGE_ROLES);
        } else if (userDTO.is("HR")) {
            entityModel = assembler.toModel(userDTO,
                    CAN_BOOK_FOR_EMPLOYEES, CAN_NOT_MANAGE_OFFICES, CAN_NOT_MANAGE_ROLES);
        } else {
            entityModel = assembler.toModel(userDTO,
                    CAN_NOT_BOOK_FOR_EMPLOYEES, CAN_NOT_MANAGE_OFFICES, CAN_NOT_MANAGE_ROLES);
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
