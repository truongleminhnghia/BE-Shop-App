package org.project.beecommerceproject.services;

import org.project.beecommerceproject.dtos.requests.UserRegisterRequest;
import org.project.beecommerceproject.entities.User;
import org.project.beecommerceproject.enums.EnumStatusUser;
import org.project.beecommerceproject.enums.ErrorCode;
import org.project.beecommerceproject.exceptions.AppException;
import org.project.beecommerceproject.src.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImp implements AdminService {

    @Autowired
    private UserService userService;

    @Override
    public User registerUser(UserRegisterRequest request) {
        boolean isAdmin = AppUtils.checkAdmin();
        if (!isAdmin) {
            throw new AppException(ErrorCode.NO_ACCESS);
        }
        return userService.save(request, isAdmin);
    }

    @Override
    public boolean lockAccount(String id) {
        boolean isLocked = false;
        User user = userService.getById(id);
        if (user == null) {
            isLocked = false;
        }
        if (user.isLockUser() && user.getStatus().equals(EnumStatusUser.ACTIVE)) {
            throw  new AppException(ErrorCode.USER_HAS_LOCKED);
        }
        boolean result = userService.lockUser(id);
        if(result) {
            user.setStatus(EnumStatusUser.INACTIVE);
            isLocked = true;
        }
        return isLocked;
    }

    @Override
    public List<User> getAll() {
        return userService.getAll();
    }
}
