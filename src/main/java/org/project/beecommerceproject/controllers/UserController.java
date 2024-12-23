package org.project.beecommerceproject.controllers;

import org.project.beecommerceproject.dtos.responses.ApiResponse;
import org.project.beecommerceproject.entities.User;
import org.project.beecommerceproject.enums.ErrorCode;
import org.project.beecommerceproject.exceptions.AppException;
import org.project.beecommerceproject.mappers.UserMapper;
import org.project.beecommerceproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/user_id/{user_id}")
    public ResponseEntity<ApiResponse> getById(@PathVariable("user_id") String id) {
        User userExisting = userService.getById(id);
        if (userExisting == null) {
            throw new AppException(ErrorCode.USER_DO_NOT_EXIST);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(200, true, "success", userMapper.toUserResponse(userExisting)));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse> getByEmail(@PathVariable("email") String email) {
        User userExisting = userService.getByEmail(email);
        if (userExisting == null) {
            throw new AppException(ErrorCode.USER_DO_NOT_EXIST);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(200, true, "success", userMapper.toUserResponse(userExisting)));
    }


}
