package com.jafarov.quiz.admin.mapper;

import com.jafarov.quiz.admin.dto.answer.AnswerInsertRequest;
import com.jafarov.quiz.admin.dto.answer.AnswerUpdateRequest;
import com.jafarov.quiz.admin.dto.answer.AnswerEditDto;
import com.jafarov.quiz.admin.entity.Answer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnswerMapper {

    Answer toDboFromInsert(AnswerInsertRequest dto);
    Answer toDboFromUpdate(AnswerUpdateRequest dto);
    AnswerEditDto toDto(Answer entity);
}
