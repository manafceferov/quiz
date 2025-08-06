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
public class TopicService {

    private final TopicRepository repository;
    private final TopicMapper mapper;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public TopicService(TopicRepository repository,
                        TopicMapper mapper,
                        QuestionRepository questionRepository,
                        AnswerRepository answerRepository
    ) {
        this.repository = repository;
        this.mapper = mapper;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
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

    public List<TopicWithQuestionCountProjection> getAllWithQuestionCount() {
        return repository.searchByNameWithQuestionCount(""); // boş string = hamısı
    }

    @Transactional
    public void update(TopicUpdateRequest request) {
        Topic updatedTopic = mapper.toDboQuizTopicFromQuizTopicUpdateRequest(request);
        repository.save(updatedTopic);
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
        return repository.findByNameContainingIgnoreCase(name.trim(), pageable);
    }

    @Transactional
    public void changeStatus(Long id, Boolean status) {
        // Topic-in statusunu dəyiş
        repository.changeStatus(id, status);

        // Əgər Topic deaktiv edilirsə (status = false), bağlı Question və Answer-ləri də deaktiv et
        if (!status) {
            // Topic-ə bağlı bütün Question-lərin statusunu false et
            questionRepository.deactivateByTopicId(id);

            // Topic-ə bağlı bütün Answer-lərin statusunu false et
            answerRepository.deactivateByTopicId(id);
        }
    }
//    public Page<TopicWithQuestionCountProjectionDto> getAllTopicsWithQuestionsCount(Pageable pageable) {
//        Page<Topic> topics = repository.findAllByIsActiveTrue(pageable);
//
//        topics.
//
//
//    }
}
