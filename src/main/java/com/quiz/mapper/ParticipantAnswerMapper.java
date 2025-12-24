package com.quiz.mapper;

import com.quiz.dto.paticipantquiz.ParticipantAnswerInsertRequest;
import com.quiz.entity.ParticipantAnswer;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ParticipantAnswerMapper {

    ParticipantAnswer toEntity(ParticipantAnswerInsertRequest dto);

    List<ParticipantAnswer> toParticipantAnswerList(List<ParticipantAnswerInsertRequest> answers);
}