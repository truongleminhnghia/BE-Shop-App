package org.project.beecommerceproject.mappers;

import org.mapstruct.Mapper;
import org.project.beecommerceproject.dtos.requests.TokenRequest;
import org.project.beecommerceproject.entities.Token;

@Mapper(componentModel = "spring")
public interface TokenMapper {
    Token toToken(TokenRequest request);
}
