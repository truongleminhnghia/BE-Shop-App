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
import org.project.beecommerceproject.src.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public User save(UserRegisterRequest request, boolean isAdmin) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.toUser(request);
        Role role = null;
        if (isAdmin) {
            role = getRole(request, isAdmin);
        } else {
            role = roleService.getRoleByName(EnumRoleName.ROLE_USER);
        }
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setStatus(EnumStatusUser.ACTIVE);
        return userRepository.save(user);
    }

    private Role getRole(UserRegisterRequest request, boolean isAdmin) {
        Role role = null;
        if (!isAdmin) {
            role = roleService.getRoleByName(EnumRoleName.ROLE_USER);
        } else {
            if (request.getRoleName().equals(EnumRoleName.ROLE_ADMIN.name())) {
                role = roleService.getRoleByName(EnumRoleName.ROLE_ADMIN);
            } else if (request.getRoleName().equals(EnumRoleName.ROLE_USER.name())) {
                role = roleService.getRoleByName(EnumRoleName.ROLE_USER);
            } else if (request.getRoleName().equals(EnumRoleName.ROLE_STAFF.name())) {
                role = roleService.getRoleByName(EnumRoleName.ROLE_STAFF);
            }
        }
        return role;
    }

    @Override
    public User getByEmail(String email) {
        String idModified = AppUtils.cutString(email);
        if (idModified == null) {
            throw new AppException(ErrorCode.USER_ID_NOT_NULL);
        }
        User user = userRepository.findByEmail(idModified);
        if (user == null) {
            throw new AppException(ErrorCode.EMAIL_NOT_NULL);
        }
        return user;
    }

    @Override
    public User getById(String id) {
        boolean isAdmin = AppUtils.checkAdmin();
        boolean checkUserCurrent = AppUtils.checkUserCurrent(id);
        if (!isAdmin && !checkUserCurrent) {
            throw new AppException(ErrorCode.NO_ACCESS);
        }
        return userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_DO_NOT_EXIST));
    }

    @Override
    public List<User> getAll() {
        List<User> users = userRepository.findAll();
        return users;
    }

    @Override
    public User update(String id, UserUpdateRequest request) {
        boolean checkUserCurrent = AppUtils.checkUserCurrent(id);
        if (!checkUserCurrent) {
            throw new AppException(ErrorCode.NO_ACCESS);
        }
        User user = getById(id);
        if (user == null) {
            throw new AppException(ErrorCode.USER_DO_NOT_EXIST);
        }
        if (request.getFirstName() != null) {
            user.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            user.setLastName(request.getLastName());
        }
        if (request.getPhoneNUmber() != null) {
            user.setAddress(request.getAddress());
        }
        if (request.getAddress() != null) {
            user.setAddress(request.getAddress());
        }
        if (request.getAddress() != null) {
            user.setAvatar(request.getAvatar());
        }
        return userRepository.save(user);
    }

    @Override
    public boolean delete(String id) {
        boolean result;
        User user = getById(id);
        if (user == null) {
            result = false;
        } else {
            user.setStatus(EnumStatusUser.INACTIVE);
            userRepository.save(user);
            result = true;
        }
        return result;
    }

    @Override
    @Transactional
    public boolean lockUser(String id) {
        return userRepository.lockUser(id) > 0;
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
