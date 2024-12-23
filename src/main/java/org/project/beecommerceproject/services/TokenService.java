package org.project.beecommerceproject.services;

import org.project.beecommerceproject.dtos.requests.TokenRequest;
import org.project.beecommerceproject.entities.Token;

public interface TokenService {
    Token save(TokenRequest request);
    Token getById(String id);
    boolean exitById(String id);
}
