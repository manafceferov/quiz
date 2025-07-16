package com.jafarov.quiz.admin.mapper;

import com.jafarov.quiz.admin.dto.question.QuestionEditDto;
import com.jafarov.quiz.admin.dto.question.QuestionInsertRequest;
import com.jafarov.quiz.admin.dto.question.QuestionUpdateRequest;
import com.jafarov.quiz.admin.entity.Question;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    Question toDboQuestionFromQuestionInsertRequest(QuestionInsertRequest dto);

    Question toDboQuestionFromQuestionUpdateRequest(QuestionUpdateRequest dto);

    QuestionEditDto toDto(Question entity);


}
