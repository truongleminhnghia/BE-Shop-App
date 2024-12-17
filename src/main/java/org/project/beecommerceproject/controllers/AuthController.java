package org.project.beecommerceproject.controllers;

import org.project.beecommerceproject.configs.CustomerDetail;
import org.project.beecommerceproject.dtos.requests.LoginRequest;
import org.project.beecommerceproject.dtos.requests.UserRegisterRequest;
import org.project.beecommerceproject.dtos.responses.ApiResponse;
import org.project.beecommerceproject.entities.Role;
import org.project.beecommerceproject.entities.User;
import org.project.beecommerceproject.enums.EnumRoleName;
import org.project.beecommerceproject.enums.EnumStatusUser;
import org.project.beecommerceproject.enums.EnumTypeLogin;
import org.project.beecommerceproject.mappers.UserMapper;
import org.project.beecommerceproject.repositories.UserRepository;
import org.project.beecommerceproject.services.JwtService;
import org.project.beecommerceproject.services.RoleService;
import org.project.beecommerceproject.services.UserServiceImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserServiceImp userServiceImp;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest request, @RequestParam(value = "type_login") String type) {
        if (type.equals(EnumTypeLogin.TYPE_LOCAL.name())) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            if (authentication.isAuthenticated()) {
                String token = jwtService.generateToken((CustomerDetail) userServiceImp.loadUserByUsername(request.getEmail()));
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ApiResponse(200, true, "Login successfully", token));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse(400, false, "Login failed", null));
            }
        } else {
            return null;
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody UserRegisterRequest request) {
        User user = userMapper.toUser(request);
        Role role = roleService.getRoleByName(EnumRoleName.ROLE_USER);
        user.setRole(role);
        user.setStatus(EnumStatusUser.ACTIVE);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userServiceImp.save(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(200, true, "User registered", userMapper.toUserResponse(user)));
    }
}
