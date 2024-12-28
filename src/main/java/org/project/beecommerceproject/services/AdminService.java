package org.project.beecommerceproject.services;

import org.project.beecommerceproject.dtos.requests.UserRegisterRequest;
import org.project.beecommerceproject.entities.User;

import java.util.List;

public interface AdminService {

    User registerUser(UserRegisterRequest request);
    boolean lockAccount(String id);
    List<User> getAll();

}
