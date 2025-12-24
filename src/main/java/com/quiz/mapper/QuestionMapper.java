package com.quiz.mapper;

import com.quiz.dto.exam.QuestionExamDto;
import com.quiz.dto.question.QuestionEditDto;
import com.quiz.dto.question.QuestionInsertRequest;
import com.quiz.dto.question.QuestionUpdateRequest;
import com.quiz.entity.Question;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    Question toDboQuestionFromQuestionInsertRequest(QuestionInsertRequest dto);

    Question toDboQuestionFromQuestionUpdateRequest(QuestionUpdateRequest dto);

    QuestionEditDto toQuestionEditDtoFromQuestionDbo(Question entity);

    QuestionExamDto toQuestionExamDtoFromQuestionDbo(Question entity);

}
