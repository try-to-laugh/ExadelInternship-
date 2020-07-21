package com.hcb.hotchairs.controllers;

import com.hcb.hotchairs.services.ICityService;
import com.hcb.hotchairs.services.impl.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cities")
public class CityController {

    private final ICityService cityService;

    @Autowired
    public CityController(ICityService cityService){
        this.cityService = cityService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(cityService.getById(id));
    }

    @GetMapping("/byCountry/{id}")
    public ResponseEntity<Object> getAllByCountryId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(cityService.getAllByCountryId(id));
    }

    @GetMapping("")
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok(cityService.getAll());
    }
}