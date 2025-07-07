package com.jafarov.quiz.admin.mapper;

import com.jafarov.quiz.admin.dto.user.UserInsertRequest;
import com.jafarov.quiz.admin.dto.user.UserUpdateRequest;
import com.jafarov.quiz.admin.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toDboUserFromUserInsertRequest(UserInsertRequest from);
    User toDboUserFromUserUpdateRequest(UserUpdateRequest from);
}
