package com.jafarov.quiz.mapper;

import com.jafarov.quiz.dto.participant.ParticipantEditDto;
import com.jafarov.quiz.dto.participant.ParticipantListDto;
import com.jafarov.quiz.dto.profile.ProfileProjectionEditDto;
import com.jafarov.quiz.entity.Participant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ParticipantMapper {

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "attachment", ignore = true)
    Participant toEntity(ParticipantEditDto participantEditDto);

    @Mapping(target = "attachmentUrl", source = "attachment.fileUrl")
    ParticipantEditDto toDto(Participant participant);

    @Mapping(source = "attachment.id", target = "attachment")
    ProfileProjectionEditDto toEditDto(Participant participant);

    @Mapping(target = "attachment.id", source = "attachment")
    Participant toEntity(ProfileProjectionEditDto dto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "fatherName", source = "fatherName")
    @Mapping(target = "status", source = "status")
    ParticipantListDto toListDto(Participant participant);

}