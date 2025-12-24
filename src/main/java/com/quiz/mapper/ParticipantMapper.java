package com.quiz.mapper;

import com.quiz.dto.participant.ParticipantEditDto;
import com.quiz.dto.participant.ParticipantListDto;
import com.quiz.dto.profile.ProfileProjectionEditDto;
import com.quiz.entity.Participant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ParticipantMapper {


    Participant toEntity(ParticipantEditDto participantEditDto);

    ParticipantEditDto toDto(Participant participant);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    void updateParticipantFromProfileProjectionEditDto(ProfileProjectionEditDto dto, @MappingTarget Participant participant);

    ParticipantListDto toListDto(Participant participant);
}
