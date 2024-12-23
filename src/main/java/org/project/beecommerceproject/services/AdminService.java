package org.project.beecommerceproject.services;

import org.project.beecommerceproject.dtos.requests.UserRegisterRequest;
import org.project.beecommerceproject.entities.User;

public interface AdminService {

    User registerUser(UserRegisterRequest request);
    boolean lockAccount(String id);

}
