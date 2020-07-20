package com.hcb.hotchairs.controllers;

import com.hcb.hotchairs.services.IPlaceService;
import com.hcb.hotchairs.services.impl.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/places")
public class PlaceController {

    private final IPlaceService placeService;

    @Autowired
    public PlaceController(IPlaceService placeService){
        this.placeService = placeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(placeService.getById(id));
    }

    @GetMapping("")
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok(placeService.getAll());
    }
}