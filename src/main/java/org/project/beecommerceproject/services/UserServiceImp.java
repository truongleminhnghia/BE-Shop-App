package org.project.beecommerceproject.services;

import org.project.beecommerceproject.configs.CustomerUserDetail;
import org.project.beecommerceproject.dtos.requests.UserRegisterRequest;
import org.project.beecommerceproject.dtos.requests.UserUpdateRequest;
import org.project.beecommerceproject.entities.Role;
import org.project.beecommerceproject.entities.User;
import org.project.beecommerceproject.enums.EnumRoleName;
import org.project.beecommerceproject.enums.ErrorCode;
import org.project.beecommerceproject.exceptions.AppException;
import org.project.beecommerceproject.mappers.UserMapper;
import org.project.beecommerceproject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User save(User user) {
        if (userRepository.existsByEmail(user.getEmail()))
            throw new AppException(ErrorCode.USER_EXISTED);
        return userRepository.save(user);
    }

    @Override
    public User getByEmail(String email) {
        return null;
    }

    @Override
    public User getById(String id) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return List.of();
    }

    @Override
    public User update(String id, UserUpdateRequest request) {
        return null;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return CustomerUserDetail.mapUserToUserDetail(user);
        } else {
            throw new UsernameNotFoundException(email);
        }
    }
}
