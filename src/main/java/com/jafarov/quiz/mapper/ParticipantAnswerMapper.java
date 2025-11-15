package com.jafarov.quiz.mapper;

import com.jafarov.quiz.dto.paticipantquiz.ParticipantAnswerInsertRequest;
import com.jafarov.quiz.entity.ParticipantAnswer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ParticipantAnswerMapper {

    ParticipantAnswer toEntity(ParticipantAnswerInsertRequest dto);

    List<ParticipantAnswer> toParticipantAnswerList(List<ParticipantAnswerInsertRequest> answers);
}