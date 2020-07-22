package com.hcb.hotchairs.mas;

import com.hcb.hotchairs.controllers.UserController;
import com.hcb.hotchairs.dtos.UserDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserDTOModelAssembler implements RepresentationModelAssembler<UserDTO, EntityModel<UserDTO>> {

    @Override
    public EntityModel<UserDTO> toModel(UserDTO userDTO) {
        return EntityModel.of(userDTO, linkTo(methodOn(UserController.class).getById(userDTO.getId())).withSelfRel());
    }

    public EntityModel<UserDTO> toModel(UserDTO userDTO,
                                        Link staffBookings, Link officeManager, Link roleManager) {
        return EntityModel.of(userDTO,
                staffBookings, officeManager, roleManager);
    }
}
