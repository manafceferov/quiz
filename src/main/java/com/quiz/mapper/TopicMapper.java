package com.quiz.mapper;

import com.quiz.dto.topic.TopicEditDto;
import com.quiz.dto.topic.TopicInsertRequest;
import com.quiz.dto.topic.TopicUpdateRequest;
import com.quiz.entity.Topic;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TopicMapper {

    @Mapping(target = "byParticipant", source = "byParticipant")
    Topic toDboQuizTopicFromQuizTopicInsertRequest(TopicInsertRequest dto);

    Topic toDboQuizTopicFromQuizTopicUpdateRequest(TopicUpdateRequest dto);

    TopicEditDto toDto(Topic entity);
}
