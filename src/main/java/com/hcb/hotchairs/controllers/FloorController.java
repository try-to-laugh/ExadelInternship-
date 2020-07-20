package com.hcb.hotchairs.controllers;

import com.hcb.hotchairs.converters.FloorConverter;
import com.hcb.hotchairs.services.IFloorService;
import com.hcb.hotchairs.services.impl.FloorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/floors")
public class FloorController {

    private final IFloorService floorService;

    @Autowired
    FloorController(IFloorService floorService){
        this.floorService = floorService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(floorService.getById(id));
    }

    @GetMapping("")
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok(floorService.getAll());
    }
}