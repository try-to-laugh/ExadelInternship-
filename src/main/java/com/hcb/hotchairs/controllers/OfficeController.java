package com.hcb.hotchairs.controllers;

import com.hcb.hotchairs.services.IOfficeService;
import com.hcb.hotchairs.services.impl.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Access;

@RestController
@RequestMapping("/offices")
public class OfficeController {

    private final IOfficeService officeService;

    @Autowired
    public OfficeController(IOfficeService officeService) {
        this.officeService = officeService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(officeService.getById(id));
    }

    @GetMapping("/byCity/{id}")
    public ResponseEntity<Object> getByCityId(@PathVariable("id") Long id){
        return ResponseEntity.ok(officeService.getAllByCityId((id)));
    }

    @GetMapping("")
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok(officeService.getAll());
    }
}