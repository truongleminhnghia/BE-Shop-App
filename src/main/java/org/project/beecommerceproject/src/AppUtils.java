package org.project.beecommerceproject.src;

import org.project.beecommerceproject.configs.CustomerUserDetail;
import org.springframework.security.core.context.SecurityContextHolder;

public class AppUtils {
    public static CustomerUserDetail getCustomerUserDetail() {
        return (CustomerUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static boolean checkAdmin() {
        CustomerUserDetail customerUserDetail = getCustomerUserDetail();
        boolean isAdmin = true;
        if (customerUserDetail == null) {
            isAdmin = false;
        } else if (customerUserDetail.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
            isAdmin = true;
        }
        return isAdmin;
    }

    public static String getUserIdCurrentUser() {
        String userIdCurrent = null;
        CustomerUserDetail customerUserDetail = getCustomerUserDetail();
        if (customerUserDetail != null) {
            userIdCurrent = customerUserDetail.getUserID();
        }
        return userIdCurrent;
    }

    public static String cutString(String str) {
        String modifiedString = str.trim();
        if(modifiedString.isEmpty() || modifiedString == null) {
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
