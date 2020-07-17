package com.hcb.hotchairs.controllers;

import com.hcb.hotchairs.converters.UserConverter;
import com.hcb.hotchairs.dtos.UserDTO;
import com.hcb.hotchairs.entities.User;
import com.hcb.hotchairs.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
    public ResponseEntity<EntityModel<UserDTO>> getById(@PathVariable("id") Long id) {

        User user = userService.getById(id);

        EntityModel<UserDTO> entityModel;

        if(user.getRoles().contains("admin")){

            entityModel= EntityModel.of(userConverter.toDTO(user),
                    linkTo(methodOn(UserController.class).getById(userConverter.toDTO(user).getId())).withSelfRel(),
                    new Link("ссыль", "My Bookings"),
                    new Link("ссыль", "Staff Bookings"),
                    new Link("ссыль", "Manage Offices"),
                    new Link("ссыль", "Manage Roles"));
        }else if(user.getRoles().contains("HR")){

            entityModel= EntityModel.of(userConverter.toDTO(user),
                    linkTo(methodOn(UserController.class).getById(userConverter.toDTO(user).getId())).withSelfRel(),
                    new Link("ссыль", "My Bookings"),
                    new Link("ссыль", "Staff Bookings"),
                    new Link("none", "Manage Offices"),
                    new Link("none", "Manage Roles"));
        }else if(user.getRoles().contains("editor")){

            entityModel= EntityModel.of(userConverter.toDTO(user),
                    linkTo(methodOn(UserController.class).getById(userConverter.toDTO(user).getId())).withSelfRel(),
                    new Link("ссыль", "My Bookings"),
                    new Link("none", "Staff Bookings"),
                    new Link("ссыль", "Manage Offices"),
                    new Link("none", "Manage Roles"));
        }else {

            entityModel = EntityModel.of(userConverter.toDTO(user),
                    linkTo(methodOn(UserController.class).getById(userConverter.toDTO(user).getId())).withSelfRel(),
                    new Link("ссыль", "My Bookings"),
                    new Link("none", "Staff Bookings"),
                    new Link("none", "Manage Offices"),
                    new Link("none", "Manage Roles"));
        }

        return Objects.isNull(user) ?
                ResponseEntity.notFound().build() :
                ResponseEntity //
                        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                        .body(entityModel);
    }

    @GetMapping("/")
    public List<UserDTO> getAll() {
        return userService.getAll().stream().map(userConverter::toDTO).collect(Collectors.toList());
    }
}
