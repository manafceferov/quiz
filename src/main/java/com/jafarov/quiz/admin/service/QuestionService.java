package com.jafarov.quiz.admin.service;

import com.jafarov.quiz.admin.dto.answer.AnswerInsertRequest;
import com.jafarov.quiz.admin.dto.question.QuestionEditDto;
import com.jafarov.quiz.admin.dto.question.QuestionInsertRequest;
import com.jafarov.quiz.admin.dto.question.QuestionUpdateRequest;
import com.jafarov.quiz.admin.entity.Answer;
import com.jafarov.quiz.admin.entity.Question;
import com.jafarov.quiz.admin.mapper.AnswerMapper;
import com.jafarov.quiz.admin.mapper.QuestionMapper;
import com.jafarov.quiz.admin.repository.QuestionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class QuestionService {

    private final QuestionRepository repository;
    private final QuestionMapper mapper;
    private final AnswerService answerService;
    private final AnswerMapper answerMapper;

    public QuestionService(
            QuestionRepository repository,
            QuestionMapper mapper,
            AnswerService answerService,
            AnswerMapper answerMapper

    ) {
        this.repository = repository;
        this.mapper = mapper;
        this.answerService = answerService;
        this.answerMapper = answerMapper;
    }

    @Transactional
    public void save(QuestionInsertRequest request, int correctAnswerIndex) {
        Question question = repository.saveAndFlush(mapper.toDboQuestionFromQuestionInsertRequest(request));

        Objects.requireNonNull(request.getAnswers()).forEach(
                x -> {
                    x.setQuestionId(question.getId());
                    x.setCorrect(correctAnswerIndex == request.getAnswers().indexOf(x));
                });

        answerService.saveAll(request.getAnswers());
    }

    public QuestionEditDto getById(Long id) {
        Question entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sual tapılmadı: " + id));
        return mapper.toQuestionEditDtoFromQuestionDbo(entity);
    }

    public Page<Question> getQuestionsByTopicId(Long topicId, Pageable pageable) {

        return repository.findByTopicId(topicId, pageable);
    }

    @Transactional
    public void update(QuestionUpdateRequest request, int correctAnswerIndex) {
        Question question = mapper.toDboQuestionFromQuestionUpdateRequest(request);

        List<Long> incomingIds = request.getAnswers().stream()
                .map(AnswerInsertRequest::getId)
                .filter(Objects::nonNull)
                .toList();
        answerService.deleteByIds(request.getId(), incomingIds);

        List<Answer> answers = new ArrayList<>();
        for (int i = 0; i < request.getAnswers().size(); i++) {
            AnswerInsertRequest answerDto = request.getAnswers().get(i);
            Answer answer = answerMapper.toDboFromInsert(answerDto);
            answer.setQuestion(question);
            answer.setCorrect(i == correctAnswerIndex);
            answers.add(answer);
        }

        question.setAnswers(new HashSet<>(answers));
        repository.save(question);
    }


    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public Optional<Question> findById(Long id) {
        return repository.findById(id);
    }

    public Page<Question> searchQuestionsByTopicAndKeyword(Long topicId, String keyword, Pageable pageable) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return repository.findByTopicId(topicId, pageable);
        }
        return repository.findByTopicIdAndQuestionContainingIgnoreCase(topicId, keyword.trim(), pageable);
    }

    public QuestionEditDto getQuestionWithAnswersById(Long id) {
        Question data = repository.getQuestionWithAnswersById(id);

        return mapper.toQuestionEditDtoFromQuestionDbo(data);
    }
}
