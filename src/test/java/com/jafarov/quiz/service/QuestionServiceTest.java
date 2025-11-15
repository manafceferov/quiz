package com.jafarov.quiz.service;

import com.jafarov.quiz.dto.answer.AnswerInsertRequest;
import com.jafarov.quiz.dto.question.QuestionEditDto;
import com.jafarov.quiz.dto.question.QuestionInsertRequest;
import com.jafarov.quiz.entity.Question;
import com.jafarov.quiz.mapper.AnswerMapper;
import com.jafarov.quiz.mapper.QuestionMapper;
import com.jafarov.quiz.repository.QuestionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuestionServiceTest {

    @Mock
    private QuestionRepository repository;

    @Mock
    private QuestionMapper mapper;

    @Mock
    private AnswerService answerService;

    @Mock
    private AnswerMapper answerMapper;

    @InjectMocks
    private QuestionService questionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        QuestionInsertRequest request = new QuestionInsertRequest();
        request.setAnswers(Arrays.asList(new AnswerInsertRequest(), new AnswerInsertRequest()));
        Question savedQuestion = new Question();
        savedQuestion.setId(1L);

        when(mapper.toDboQuestionFromQuestionInsertRequest(request)).thenReturn(savedQuestion);
        when(repository.saveAndFlush(savedQuestion)).thenReturn(savedQuestion);

        questionService.save(request, 0);

        verify(repository, times(1)).saveAndFlush(savedQuestion);
        verify(answerService, times(1)).saveAll(request.getAnswers());
    }

    @Test
    void testGetByIdFound() {
        Question question = new Question();
        QuestionEditDto dto = new QuestionEditDto();
        when(repository.findById(1L)).thenReturn(Optional.of(question));
        when(mapper.toQuestionEditDtoFromQuestionDbo(question)).thenReturn(dto);

        QuestionEditDto result = questionService.getById(1L);

        assertEquals(dto, result);
    }

    @Test
    void testGetByIdNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> questionService.getById(1L));
    }

    @Test
    void testGetQuestionsByTopicId() {
        Page<Question> page = new PageImpl<>(List.of(new Question()));
        when(repository.findByTopicId(anyLong(), any(Pageable.class))).thenReturn(page);

        Page<Question> result = questionService.getQuestionsByTopicId(1L, Pageable.unpaged());
        assertEquals(page, result);
    }

    @Test
    void testDeleteById() {
        questionService.deleteById(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testFindById() {
        Question question = new Question();
        when(repository.findById(1L)).thenReturn(Optional.of(question));

        Optional<Question> result = questionService.findById(1L);
        assertTrue(result.isPresent());
        assertEquals(question, result.get());
    }

    @Test
    void testSearchQuestionsByTopicAndKeyword() {
        Page<Question> page = new PageImpl<>(List.of(new Question()));
        when(repository.findByTopicIdAndQuestionContainingIgnoreCase(1L, "keyword", Pageable.unpaged())).thenReturn(page);

        Page<Question> result = questionService.searchQuestionsByTopicAndKeyword(1L, "keyword", Pageable.unpaged());
        assertEquals(page, result);
    }

    @Test
    void testGetQuestionCountByTopicId() {
        when(repository.getCountByTopicId(1L)).thenReturn(5L);
        Long count = questionService.getQuestionCountByTopicId(1L);
        assertEquals(5L, count);
    }
}
