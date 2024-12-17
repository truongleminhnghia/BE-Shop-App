package org.project.beecommerceproject.services;

import org.project.beecommerceproject.dtos.requests.UserUpdateRequest;
import org.project.beecommerceproject.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    User save(User user);
    User getByEmail(String email);
    User getById(String id);
    List<User> getAll();
    User update(String id, UserUpdateRequest request);
    boolean delete(String id);
}
