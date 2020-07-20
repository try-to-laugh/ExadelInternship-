package com.hcb.hotchairs.controllers;

import com.hcb.hotchairs.services.IDBRestoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restore")
public class DBRestoreController {

    private final IDBRestoreService restoreService;

    @Autowired
    public DBRestoreController(IDBRestoreService restoreService) {
        this.restoreService = restoreService;
    }

    @GetMapping("/all")
    public ResponseEntity<String> restoreDB() {
        restoreService.restore();

        return ResponseEntity.ok("Database restored");
    }
}
