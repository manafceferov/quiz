package com.jafarov.quiz.admin.service;

import com.jafarov.quiz.admin.dto.answer.AnswerInsertRequest;
import com.jafarov.quiz.admin.dto.answer.AnswerUpdateRequest;
import com.jafarov.quiz.admin.dto.answer.AnswerEditDto;
import com.jafarov.quiz.admin.dto.question.QuestionInsertRequest;
import com.jafarov.quiz.admin.entity.Answer;
import com.jafarov.quiz.admin.mapper.AnswerMapper;
import com.jafarov.quiz.admin.mapper.QuestionMapper;
import com.jafarov.quiz.admin.repository.AnswerRepository;
import com.jafarov.quiz.admin.repository.QuestionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnswerService {

    private final AnswerRepository repository;
    private final AnswerMapper mapper;
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;


    public AnswerService(AnswerRepository repository, AnswerMapper mapper,
                         QuestionRepository questionRepository, QuestionMapper questionMapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.questionRepository = questionRepository;
        this.questionMapper = questionMapper;

    }

    public List<Answer> getAnswersByQuestionId(Long questionId) {

        return repository.findByQuestionId(questionId);
    }

    @Transactional
    public void save(AnswerInsertRequest request) {
        Answer answer = mapper.toDboFromInsert(request);
        repository.save(answer);
    }

    @Transactional
    public void saveAll(List<AnswerInsertRequest> requestList) {
        repository.saveAll(mapper.toDboFromAnswerInsertRequest(requestList));
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

    @Transactional
    public void deleteByIds(Long questionId, List<Long> ids) {
        repository.deleteByIds(questionId, ids);
    }

}

