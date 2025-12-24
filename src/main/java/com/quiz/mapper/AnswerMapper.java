package com.quiz.mapper;

import com.quiz.dto.answer.AnswerInsertRequest;
import com.quiz.dto.answer.AnswerUpdateRequest;
import com.quiz.dto.answer.AnswerEditDto;
import com.quiz.dto.exam.AnswerExamDto;
import com.quiz.entity.Answer;
import com.quiz.entity.Question;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring", imports = Question.class)
public interface AnswerMapper {

    Answer toDboFromInsert(AnswerInsertRequest from);

    List<Answer> toDboFromAnswerInsertRequest(List<AnswerInsertRequest> from);

    Answer toDboFromUpdate(AnswerUpdateRequest from);

    List<Answer> toDboFromAnswerUpdateRequest(List<AnswerUpdateRequest> from);

    AnswerEditDto toDto(Answer from);

    AnswerExamDto toAnswerExamDto(Answer from);

    List<AnswerExamDto> toAnswerExamDtoList(List<Answer> answers);

}