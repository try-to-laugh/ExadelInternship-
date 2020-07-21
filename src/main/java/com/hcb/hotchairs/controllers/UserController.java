package com.hcb.hotchairs.controllers;

import com.hcb.hotchairs.dtos.UserDTO;
import com.hcb.hotchairs.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<UserDTO>> getById(@PathVariable("id") Long id) {

        EntityModel<UserDTO> entityModel;
        UserDTO userDTO = userService.getById(id);

        if (userDTO.getRoles().stream().filter(roleDTO -> roleDTO.getName().equals("All")).findFirst().orElse(null)
                != null) {
            entityModel = EntityModel.of(userDTO, //
                    linkTo(methodOn(UserController.class).getById(userDTO.getId())).withSelfRel(),
                    new Link("here", "Staff Bookings"),
                    new Link("here", "Manage Offices"),
                    new Link("here", "Manage Roles"));
        } else if (userDTO.getRoles().stream().filter(roleDTO -> roleDTO.getName().equals("HR")).findFirst().orElse(null)
                != null) {
            entityModel = EntityModel.of(userDTO, //
                    linkTo(methodOn(UserController.class).getById(userDTO.getId())).withSelfRel(),
                    new Link("here", "Staff Bookings"),
                    new Link("none", "Manage Offices"),
                    new Link("none", "Manage Roles"));
        } else if (userDTO.getRoles().stream().filter(roleDTO -> roleDTO.getName().equals("Office Manager")).findFirst().orElse(null)
                != null) {
            entityModel = EntityModel.of(userDTO, //
                    linkTo(methodOn(UserController.class).getById(userDTO.getId())).withSelfRel(),
                    new Link("none", "Staff Bookings"),
                    new Link("here", "Manage Offices"),
                    new Link("none", "Manage Roles"));
        } else if (userDTO.getRoles().stream().filter(roleDTO -> roleDTO.getName().equals("Admin")).findFirst().orElse(null)
                != null) {
            entityModel = EntityModel.of(userDTO, //
                    linkTo(methodOn(UserController.class).getById(userDTO.getId())).withSelfRel(),
                    new Link("here", "Staff Bookings"),
                    new Link("here", "Manage Offices"),
                    new Link("here", "Manage Roles"));
        } else {
            entityModel = EntityModel.of(userDTO, //
                    linkTo(methodOn(UserController.class).getById(userDTO.getId())).withSelfRel(),
                    new Link("none", "Staff Bookings"),
                    new Link("none", "Manage Offices"),
                    new Link("none", "Manage Roles"));
        }

        return ResponseEntity.ok(entityModel);
    }

    @GetMapping("")
    public ResponseEntity<List<UserDTO>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }
}
