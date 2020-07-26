package com.hcb.hotchairs.controllers;

import com.hcb.hotchairs.converters.RoleConverter;
import com.hcb.hotchairs.dtos.RoleDTO;
import com.hcb.hotchairs.services.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final IRoleService roleService;

    @Autowired
    public RoleController(IRoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(roleService.getRoleById(id));
    }

    @GetMapping("")
    public ResponseEntity<List<RoleDTO>> getAll() {
        return ResponseEntity.ok(roleService.getAll());
    }
}
