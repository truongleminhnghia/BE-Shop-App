package org.project.beecommerceproject.controllers;

import org.project.beecommerceproject.dtos.requests.UserRegisterRequest;
import org.project.beecommerceproject.dtos.responses.ApiResponse;
import org.project.beecommerceproject.entities.User;
import org.project.beecommerceproject.mappers.UserMapper;
import org.project.beecommerceproject.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    @Qualifier("adminServiceImp")
    private AdminService adminService;
    @Autowired
    private UserMapper userMapper;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("register_user")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody UserRegisterRequest request) {
        User user = adminService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(201, true, "created", userMapper.toUserResponse(user)));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("lock_account/{user_id}")
    public ResponseEntity<ApiResponse> lockAccount(@PathVariable("user_id") String id) {
        boolean result = adminService.lockAccount(id);
        if (result) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, true, "Locked", null));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse(400, false, "Lock Account Failed", null));
    }


}
