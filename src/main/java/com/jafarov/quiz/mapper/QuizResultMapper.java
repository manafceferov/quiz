package com.jafarov.quiz.mapper;

import com.jafarov.quiz.dto.paticipantquiz.ParticipantQuizResultList;
import com.jafarov.quiz.dto.paticipantquiz.QuizResultInsertRequest;
import com.jafarov.quiz.entity.Participant;
import com.jafarov.quiz.entity.QuizResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface QuizResultMapper {

    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "questionsCount", source = "questionsCount")
    QuizResult toDbo(QuizResultInsertRequest request);

    @Mapping(target = "questionCount", source = "questionsCount")
    @Mapping(target = "topicName", source = "topic.name")
    ParticipantQuizResultList toParticipantQuizResultList(QuizResult quizResult);

}
