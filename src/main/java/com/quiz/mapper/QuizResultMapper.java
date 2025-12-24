package com.quiz.mapper;

import com.quiz.dto.paticipantquiz.ParticipantQuizResultList;
import com.quiz.dto.paticipantquiz.QuizResultInsertRequest;
import com.quiz.entity.QuizResult;
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
