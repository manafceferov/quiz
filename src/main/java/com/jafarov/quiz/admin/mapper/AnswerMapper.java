package com.jafarov.quiz.admin.mapper;

import com.jafarov.quiz.admin.dto.answer.AnswerInsertRequest;
import com.jafarov.quiz.admin.dto.answer.AnswerUpdateRequest;
import com.jafarov.quiz.admin.dto.answer.AnswerEditDto;
import com.jafarov.quiz.admin.dto.question.QuestionInsertRequest;
import com.jafarov.quiz.admin.entity.Answer;
import com.jafarov.quiz.admin.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", imports = Question.class)
public interface AnswerMapper {


    Answer toDboFromInsert(AnswerInsertRequest from);
    List<Answer> toDboFromAnswerInsertRequest(List<AnswerInsertRequest> from);
    Answer toDboFromUpdate(AnswerUpdateRequest from);
    List<Answer> toDboFromAnswerUpdateRequest(List<AnswerUpdateRequest> from);
    AnswerEditDto toDto(Answer from);

    Question toDboQuestionFromQuestionInsertRequest(QuestionInsertRequest from);
}
