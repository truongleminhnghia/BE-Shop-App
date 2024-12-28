package org.project.beecommerceproject.src;

import org.project.beecommerceproject.configs.CustomerUserDetail;
import org.project.beecommerceproject.enums.ErrorCode;
import org.project.beecommerceproject.exceptions.AppException;
import org.springframework.security.core.context.SecurityContextHolder;

public class AppUtils {
    public static CustomerUserDetail getCustomerUserDetail() {
        CustomerUserDetail customerUserDetail = (CustomerUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (customerUserDetail == null) {
            throw new AppException(ErrorCode.NO_CURRENT_USER);
        }
        return customerUserDetail;
    }

    public static boolean checkAdmin() {
        CustomerUserDetail customerUserDetail = getCustomerUserDetail();
        return customerUserDetail.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
    }

    public static String getUserIdCurrentUser() {
        String userIdCurrent = null;
        CustomerUserDetail customerUserDetail = getCustomerUserDetail();
        userIdCurrent = customerUserDetail.getUserID();
        return userIdCurrent;
    }

    public static String cutString(String str) {
        String modifiedString = str.trim();
        if (modifiedString.isEmpty()) {
            return null;
        }
        return modifiedString;
    }

    public static boolean checkUserCurrent(String id) {
        boolean result;
        String userId = cutString(id);
        String userIdCurrent = getUserIdCurrentUser();
        if (userIdCurrent == null || userId == null) {
            result = false;
        } else result = userId.equals(userIdCurrent);
        return result;
    }
}
