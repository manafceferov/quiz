package com.jafarov.quiz.service;

import com.jafarov.quiz.dto.topic.*;
import com.jafarov.quiz.entity.Topic;
import com.jafarov.quiz.mapper.TopicMapper;
import com.jafarov.quiz.repository.AnswerRepository;
import com.jafarov.quiz.repository.QuestionRepository;
import com.jafarov.quiz.repository.TopicRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TopicService{

    private final TopicRepository repository;
    private final TopicMapper mapper;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final QuestionService questionService;

    public TopicService(TopicRepository repository,
                        TopicMapper mapper,
                        QuestionRepository questionRepository,
                        AnswerRepository answerRepository,
                        QuestionService questionService
    ) {
        this.repository = repository;
        this.mapper = mapper;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.questionService = questionService;
    }

    @Transactional
    public void save(TopicInsertRequest request) {
        repository.save(mapper.toDboQuizTopicFromQuizTopicInsertRequest(request));
    }

    public TopicEditDto getById(Long id) {
        Topic entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mövzu tapılmadı: " + id));
        return mapper.toDto(entity);
    }

    public Page<Topic> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<TopicWithQuestionCountProjection> getAllTopics(Pageable pageable) {
        return repository.getAllTopicsWithQuestionsCount(pageable);
    }

    public List<TopicWithQuestionCountProjection> searchTopicsWithQuestionCount(String name) {
        return repository.searchByNameWithQuestionCount(name);
    }

//    public List<TopicWithQuestionCountProjection> getAllWithQuestionCount() {
//        return repository.searchByNameWithQuestionCount("");
//    }

    @Transactional
    public void update(TopicUpdateRequest request) {
        repository.save(mapper.toDboQuizTopicFromQuizTopicUpdateRequest(request));
    }

    public void deleteById(Long id) {

        repository.deleteById(id);
    }

    public Optional<Topic> findById(Long id) {

        return repository.findById(id);
    }

    public Page<Topic> getAll(String name, Pageable pageable) {
        if (name == null || name.isBlank()) {
            return repository.findAll(pageable);
        }
        return repository.searchByNameOrParticipant(name.trim(), pageable);
    }

    @Transactional
    public void changeStatus(Long id,
                             Boolean status
    ) {
        repository.changeStatus(id, status);
        if (!status) {
            questionRepository.deactivateByTopicId(id);
            answerRepository.deactivateByTopicId(id);
        }
    }

    public Page<Topic> getAllByParticipant(Long participantId,
                                           String name,
                                           Pageable pageable
    ) {
        if (name == null || name.isBlank()) {
            return repository.findByByParticipant(participantId, pageable);
        }
        return repository.findByByParticipantAndNameContainingIgnoreCase(participantId, name.trim(), pageable);
    }


}
