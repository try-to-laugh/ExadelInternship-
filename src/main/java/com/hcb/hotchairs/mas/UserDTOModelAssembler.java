package com.hcb.hotchairs.mas;

import com.hcb.hotchairs.dtos.UserDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class UserDTOModelAssembler implements RepresentationModelAssembler<UserDTO, EntityModel<UserDTO>> {

    private static final Link CAN_BOOK_FOR_EMPLOYEES = new Link("/home", "Staff Bookings");
    private static final Link CAN_MANAGE_OFFICES = new Link("/offices", "Manage Offices");
    private static final Link CAN_MANAGE_ROLES = new Link("/home", "Manage Roles");

    @Override
    public EntityModel<UserDTO> toModel(UserDTO userDTO) {

        EntityModel<UserDTO> entityModel;

        if (userDTO.is("Admin")) {
            entityModel = EntityModel.of(userDTO, CAN_BOOK_FOR_EMPLOYEES, CAN_MANAGE_OFFICES, CAN_MANAGE_ROLES);
        } else if (userDTO.is("Office Manager")) {
            entityModel = EntityModel.of(userDTO, CAN_MANAGE_OFFICES);
        } else if (userDTO.is("HR")) {
            entityModel = EntityModel.of(userDTO, CAN_BOOK_FOR_EMPLOYEES);
        } else {
            entityModel = EntityModel.of(userDTO);
        }

        /**TODO
         * 1. clarify about the roles, whether the user can have the HR and Office Manager roles,
         * and, if so, implement additional checks
         */

        return entityModel;
    }
}
