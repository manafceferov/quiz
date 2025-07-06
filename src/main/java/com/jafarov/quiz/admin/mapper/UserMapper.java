package com.jafarov.quiz.admin.mapper;

import com.jafarov.quiz.admin.dto.user.UserIUDRequest;
import com.jafarov.quiz.admin.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toDboUserFromUserIUDRequest(UserIUDRequest from);
}
