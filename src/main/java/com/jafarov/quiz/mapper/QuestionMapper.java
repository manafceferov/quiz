package com.jafarov.quiz.mapper;

import com.jafarov.quiz.dto.question.QuestionEditDto;
import com.jafarov.quiz.dto.question.QuestionInsertRequest;
import com.jafarov.quiz.dto.question.QuestionUpdateRequest;
import com.jafarov.quiz.entity.Question;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    Question toDboQuestionFromQuestionInsertRequest(QuestionInsertRequest dto);

    Question toDboQuestionFromQuestionUpdateRequest(QuestionUpdateRequest dto);

    QuestionEditDto toQuestionEditDtoFromQuestionDbo(Question entity);

}
