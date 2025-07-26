package com.jafarov.quiz.mapper;

import com.jafarov.quiz.dto.user.UserInsertRequest;
import com.jafarov.quiz.dto.user.UserUpdateRequest;
import com.jafarov.quiz.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toDboUserFromUserInsertRequest(UserInsertRequest from);
    List<User> toDboUserFromUserInsertRequest(List<UserInsertRequest> from);
    User toDboUserFromUserUpdateRequest(UserUpdateRequest from);
}
