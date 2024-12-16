package org.project.beecommerceproject.mappers;

import org.mapstruct.Mapper;
import org.project.beecommerceproject.dtos.requests.UserRegisterRequest;
import org.project.beecommerceproject.dtos.responses.UserResponse;
import org.project.beecommerceproject.entities.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserRegisterRequest request);
    UserResponse toUserResponse(User user);

    List<UserResponse> toUserResponses(List<User> users);
}
