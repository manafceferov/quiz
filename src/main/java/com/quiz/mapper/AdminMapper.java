package com.quiz.mapper;

import com.quiz.dto.admin.AdminInsertRequest;
import com.quiz.dto.admin.AdminUpdateRequest;
import com.quiz.entity.Admin;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdminMapper {

    Admin toDboUserFromUserInsertRequest(AdminInsertRequest from);
    List<Admin> toDboUserFromUserInsertRequest(List<AdminInsertRequest> from);
    Admin toDboUserFromUserUpdateRequest(AdminUpdateRequest from);
}
