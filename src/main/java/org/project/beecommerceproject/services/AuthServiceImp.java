package org.project.beecommerceproject.services;

import org.project.beecommerceproject.enums.EnumTypeLogin;
import org.project.beecommerceproject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImp implements AuthService {

    @Autowired private UserServiceImp userServiceImp;
    @Autowired private UserRepository userRepository;

    @Override
    public boolean login(String email, String password, EnumTypeLogin type) {
        return false;
    }
}
