package com.hcb.hotchairs.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/floors")
public class FloorController {

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable("id") Long id) {
        return ResponseEntity
                .status(HttpStatus.NOT_IMPLEMENTED)
                .body("Content will be here, just wait...");
    }

    @GetMapping("")
    public ResponseEntity<Object> getAll() {
        return ResponseEntity
                .status(HttpStatus.NOT_IMPLEMENTED)
                .body("Content will be here, just wait...");
    }
}