package com.jafarov.quiz.admin.mapper;

import com.jafarov.quiz.admin.dto.topic.QuizEditDto;
import com.jafarov.quiz.admin.dto.topic.QuizTopicInsertRequest;
import com.jafarov.quiz.admin.dto.topic.QuizTopicUpdateRequest;
import com.jafarov.quiz.admin.entity.Topic;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuizTopicMapper {

    Topic toDboQuizTopicFromQuizTopicInsertRequest(QuizTopicInsertRequest dto);

    Topic toDboQuizTopicFromQuizTopicUpdateRequest(QuizTopicUpdateRequest dto);

    QuizEditDto toDto(Topic entity);
}
