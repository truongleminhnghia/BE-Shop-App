package org.project.beecommerceproject.src;

import org.project.beecommerceproject.configs.CustomerUserDetail;
import org.springframework.security.core.context.SecurityContextHolder;

public class AppUtils {
    public static CustomerUserDetail getCustomerUserDetail() {
        CustomerUserDetail customerUserDetail = (CustomerUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return customerUserDetail;
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
}
