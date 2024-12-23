package org.project.beecommerceproject.services;

import org.project.beecommerceproject.dtos.requests.Oauth2Request;
import org.project.beecommerceproject.dtos.requests.UserRegisterRequest;
import org.project.beecommerceproject.entities.User;
import org.project.beecommerceproject.enums.EnumTypeLogin;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public interface AuthService {
    String login(String email, String password);
    User register(UserRegisterRequest request);
    String generateUrl(String type) throws UnsupportedEncodingException;
    Map<String, Object> authenticateAndFetchProfile(String code, String type) throws IOException;
    String loginOauth2(Oauth2Request request);


}
