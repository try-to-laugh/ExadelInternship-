package com.hcb.hotchairs.controllers;

import com.hcb.hotchairs.services.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    private final ILoginService loginService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public TestController(ILoginService loginService,
                          PasswordEncoder passwordEncoder) {
        this.loginService = loginService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/encode/{password}")
    public ResponseEntity<String> getEncodedPassword(@PathVariable String password) {
        return ResponseEntity.ok(passwordEncoder.encode(password));
    }

    @GetMapping("/logInfo/{email}")
    public ResponseEntity<UserDetails> getLoginInfo(@PathVariable String email) {
        return ResponseEntity.ok(loginService.loadUserByUsername(email));
    }

    @GetMapping("/adminAuth")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<String> checkAdminAuth() {
        return ResponseEntity.ok("Admin logged in.");
    }

    @GetMapping("/HRAuth")
    @PreAuthorize("hasAnyAuthority('Admin', 'HR')")
    public ResponseEntity<String> checkHRAuth() {
        return ResponseEntity.ok("HR logged in.");
    }

    @GetMapping("/oManagerAuth")
    @PreAuthorize("hasAnyAuthority('Admin', 'Office Manager')")
    public ResponseEntity<String> checkOManagerAuth() {
        return ResponseEntity.ok("Office Manager logged in.");
    }

    @GetMapping("/workerAuth")
    public ResponseEntity<String> checkWorkerAuth() {
        return ResponseEntity.ok("Worker logged in.");
    }
}
