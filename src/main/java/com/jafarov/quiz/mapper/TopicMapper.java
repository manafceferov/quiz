package com.jafarov.quiz.mapper;

import com.jafarov.quiz.dto.topic.TopicEditDto;
import com.jafarov.quiz.dto.topic.TopicInsertRequest;
import com.jafarov.quiz.dto.topic.TopicUpdateRequest;
import com.jafarov.quiz.entity.Topic;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TopicMapper {

    Topic toDboQuizTopicFromQuizTopicInsertRequest(TopicInsertRequest dto);

    Topic toDboQuizTopicFromQuizTopicUpdateRequest(TopicUpdateRequest dto);

    TopicEditDto toDto(Topic entity);

}
