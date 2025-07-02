package com.jafarov.quiz.admin.service;

import com.jafarov.quiz.entity.Question;
import com.jafarov.quiz.entity.QuizEntity;
import com.jafarov.quiz.repository.QuizRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final QuizRepository quizRepository;

    public AdminService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public List<QuizEntity> getAllQuizzes() {
        return quizRepository.findAll();
    }

    public void createQuiz(QuizEntity quiz) {
        quizRepository.save(quiz);
    }

    public void deleteQuiz(Long id) {
        quizRepository.deleteById(id);
    }
}

