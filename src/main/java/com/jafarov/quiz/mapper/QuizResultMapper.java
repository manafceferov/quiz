package com.jafarov.quiz.mapper;

import com.jafarov.quiz.dto.paticipantquiz.ParticipantQuizResultList;
import com.jafarov.quiz.dto.paticipantquiz.QuizResultInsertRequest;
import com.jafarov.quiz.entity.Participant;
import com.jafarov.quiz.entity.QuizResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface QuizResultMapper {

    QuizResult toDbo(QuizResultInsertRequest request);

    ParticipantQuizResultList toParticipantQuizResultList(QuizResult quizResult);

}
