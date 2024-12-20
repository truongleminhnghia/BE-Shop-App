package org.project.beecommerceproject.controllers;

import org.project.beecommerceproject.dtos.requests.RoleRequest;
import org.project.beecommerceproject.entities.Role;
import org.project.beecommerceproject.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/home")
    public String hello() {
        return "home";
    }

    @PostMapping("/create-role")
    public ResponseEntity<String> createRole(@RequestBody RoleRequest request) {
        Role role = roleService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("success");
    }
}
