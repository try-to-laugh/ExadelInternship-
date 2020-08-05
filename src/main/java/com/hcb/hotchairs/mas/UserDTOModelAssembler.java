package com.hcb.hotchairs.mas;

import com.hcb.hotchairs.dtos.UserDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class UserDTOModelAssembler implements RepresentationModelAssembler<UserDTO, EntityModel<UserDTO>> {

    private static final Link CAN_BOOK_FOR_EMPLOYEES = new Link("/staff-bookings", "Staff Bookings");
    private static final Link CAN_MANAGE_OFFICES = new Link("/offices", "Manage Offices");
    private static final Link CAN_MANAGE_ROLES = new Link("/manage-roles", "Manage Roles");
    private static final Link CAN_VIEW_BOOKINGS = new Link("/bookings", "My Bookings");

    @Override
    public EntityModel<UserDTO> toModel(UserDTO userDTO) {

        EntityModel<UserDTO> entityModel;
        ArrayList<Link> links = new ArrayList<>();

        links.add(CAN_VIEW_BOOKINGS);

        if (userDTO.is("Admin")) {
            links.addAll(List.of(CAN_MANAGE_OFFICES, CAN_MANAGE_ROLES));
        }

        if (userDTO.is("HR")) {
            links.add(CAN_BOOK_FOR_EMPLOYEES);
        }

        if (userDTO.is("Office Manager")) {
            links.add(CAN_MANAGE_OFFICES);
        }

        entityModel = EntityModel.of(userDTO, links.toArray(Link[]::new));
        return entityModel;
    }
}
