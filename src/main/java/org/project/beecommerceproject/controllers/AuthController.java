package org.project.beecommerceproject.controllers;

import org.project.beecommerceproject.dtos.requests.LoginRequest;
import org.project.beecommerceproject.dtos.requests.Oauth2Request;
import org.project.beecommerceproject.dtos.requests.TokenRequest;
import org.project.beecommerceproject.dtos.requests.UserRegisterRequest;
import org.project.beecommerceproject.dtos.responses.ApiResponse;
import org.project.beecommerceproject.entities.Token;
import org.project.beecommerceproject.entities.User;
import org.project.beecommerceproject.enums.EnumTypeLogin;
import org.project.beecommerceproject.mappers.UserMapper;
import org.project.beecommerceproject.services.AuthService;
import org.project.beecommerceproject.services.JwtService;
import org.project.beecommerceproject.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/auths")
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AuthService authService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(
            @RequestBody LoginRequest request,
            @RequestParam(value = "type_login", required = false, defaultValue = "TYPE_LOCAL") String type) throws UnsupportedEncodingException {
        if (type.equals(EnumTypeLogin.TYPE_LOCAL.name())) {
            String tokenRes = authService.login(request.getEmail(), request.getPassword());
            if (tokenRes != null) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ApiResponse(200, true, "Login successfully", tokenRes));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(400, false, "Login failed", null));
        } else if (type.equals(EnumTypeLogin.TYPE_GOOGLE.name())) {
            String urlRes = authService.generateUrl(type);
            if (urlRes == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse(400, false, "Generate URL failed", null));
            }
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(200, true, "Generate URL successfully", urlRes));
        } else {
            return null;
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody UserRegisterRequest request) {
        User user = authService.register(request);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(400, true, "Failed", null));
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(200, true, "User registered", userMapper.toUserResponse(user)));
    }

    @GetMapping("/callback")
    public ResponseEntity<ApiResponse> callBackAuthenticate(
            @RequestParam("code") String code,
            @RequestParam("type_login") String type) throws IOException {
        Map<String, Object> infoUser = authService.authenticateAndFetchProfile(code, type);
        if (infoUser == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(400, false, "failed", null));
        }
        if (type.equals(EnumTypeLogin.TYPE_GOOGLE.name())) {
            Oauth2Request oauth2Request = Oauth2Request.builder()
                    .fullName((String) Objects.requireNonNullElse(infoUser.get("name"), ""))
                    .googleAccountId((String) Objects.requireNonNullElse(infoUser.get("sub"), ""))
                    .password("")
                    .email((String) Objects.requireNonNullElse(infoUser.get("email"), ""))
                    .avatar((String) Objects.requireNonNullElse(infoUser.get("picture"), ""))
                    .phoneNumber("")
                    .facebookAccountId("")
                    .build();
            String result = authService.loginOauth2(oauth2Request);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, true, "success", result));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(400, false, "failed", null));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout(@RequestBody TokenRequest tokenRequest) {
        Token token = tokenService.save(tokenRequest);
        if (token != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, true, "Success", null));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(400, false, "Failed", null));
    }

    @GetMapping("/verify_token")
    public ResponseEntity<ApiResponse> verifyToken(@RequestBody TokenRequest request) {
        boolean result = true;
        result = jwtService.isTokenValid(request.getToken());
        if (result) return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, true, "Success", null));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(400, false, "Failed", null));
    }

    @PostMapping("/refresh_token")
    public ResponseEntity<ApiResponse> refreshToken(@RequestBody TokenRequest request) {
        String refreshToke = jwtService.refreshToken(request);
        if (refreshToke != null)
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, true, "Success", refreshToke));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(400, false, "failed", null));
    }
}
