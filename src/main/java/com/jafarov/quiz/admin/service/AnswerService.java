package com.jafarov.quiz.admin.service;

import com.jafarov.quiz.admin.dto.answer.AnswerInsertRequest;
import com.jafarov.quiz.admin.dto.answer.AnswerUpdateRequest;
import com.jafarov.quiz.admin.dto.answer.AnswerEditDto;
import com.jafarov.quiz.admin.dto.question.QuestionInsertRequest;
import com.jafarov.quiz.admin.entity.Answer;
import com.jafarov.quiz.admin.mapper.AnswerMapper;
import com.jafarov.quiz.admin.repository.AnswerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AnswerService {

    private final AnswerRepository repository;
    private final AnswerMapper mapper;

    public AnswerService(AnswerRepository repository, AnswerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<Answer> getAnswersByQuestionId(Long questionId) {
        return repository.findByQuestionId(questionId);
    }

    @Transactional
    public void insert(AnswerInsertRequest request) {
        repository.save(mapper.toDboFromInsert(request));
    }

    @Transactional
    public void update(AnswerUpdateRequest request) {
        repository.save(mapper.toDboFromUpdate(request));
    }

    public AnswerEditDto getById(Long id) {
        Answer answer = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cavab tapılmadı: " + id));
        return mapper.toDto(answer);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }



}
