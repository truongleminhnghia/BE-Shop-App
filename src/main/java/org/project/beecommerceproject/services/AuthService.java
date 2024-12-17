package org.project.beecommerceproject.services;

import org.project.beecommerceproject.enums.EnumTypeLogin;

public interface AuthService {
    boolean login(String email, String password, EnumTypeLogin type);
//    boolean register();

}
