package org.project.beecommerceproject.services;

import org.project.beecommerceproject.configs.CustomerUserDetail;
import org.project.beecommerceproject.dtos.requests.UserRegisterRequest;
import org.project.beecommerceproject.dtos.requests.UserUpdateRequest;
import org.project.beecommerceproject.entities.Role;
import org.project.beecommerceproject.entities.User;
import org.project.beecommerceproject.enums.EnumRoleName;
import org.project.beecommerceproject.enums.EnumStatusUser;
import org.project.beecommerceproject.enums.ErrorCode;
import org.project.beecommerceproject.exceptions.AppException;
import org.project.beecommerceproject.mappers.UserMapper;
import org.project.beecommerceproject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public User save(UserRegisterRequest request) {
        User user = userMapper.toUser(request);
        CustomerUserDetail customerUserDetail = (CustomerUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("customer login current: " + customerUserDetail.getEmail() + " " + customerUserDetail.getAuthorities());
        boolean isAdmin = customerUserDetail.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
        Role role = getRole(request, isAdmin);
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setStatus(EnumStatusUser.ACTIVE);
        return userRepository.save(user);
    }

    private static Role getRole(UserRegisterRequest request, boolean isAdmin) {
        Role role = new Role();
        if (!isAdmin) {
            role.setRoleName(EnumRoleName.ROLE_USER);
        } else {
            if (request.getRoleName().equals(EnumRoleName.ROLE_ADMIN.name())) {
                role.setRoleName(EnumRoleName.ROLE_ADMIN);
            } else if (request.getRoleName().equals(EnumRoleName.ROLE_USER.name())) {
                role.setRoleName(EnumRoleName.ROLE_USER);
            } else if (request.getRoleName().equals(EnumRoleName.ROLE_STAFF.name())) {
                role.setRoleName(EnumRoleName.ROLE_STAFF);
            }
        }
        return role;
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
