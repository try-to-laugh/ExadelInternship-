package com.hcb.hotchairs.controllers;

import com.hcb.hotchairs.entities.Country;
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
@RequestMapping("/countries")
public class CountryController {

    //private final ICountryService countryService;
    //private final CountryConverter countryConverter;

    /*@Autowired
    public CountryController(ICountryService countryService, CountryConverter countryConverter) {
        this.countryService = countryService;
        this.countryConverter = countryConverter;
    }*/

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable("id") Long id) {
        return ResponseEntity
                .status(HttpStatus.NOT_IMPLEMENTED)
                .body("Content will be here, just wait...");
    }

    /*public ResponseEntity<Object> getById(@PathVariable("id") Long id) {
        Country country = countryService.getById(id);
        return Objects.isNull(country) ?
                ResponseEntity.notFound().build() : ResponseEntity.ok(countryConverter.toDTO(country));
    }*/

    @GetMapping("")
    public ResponseEntity<Object> getAll() {
        return ResponseEntity
                .status(HttpStatus.NOT_IMPLEMENTED)
                .body("Content will be here, just wait...");
    }

    /*public ResponseEntity<List<CountryDTO>> getAll() {
        return ResponseEntity.ok(countryService.getAll().stream().map(countryConverter::toDTO).collect(Collectors.toList()));
    }*/
}
