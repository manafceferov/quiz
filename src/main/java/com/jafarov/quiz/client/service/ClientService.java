package com.jafarov.quiz.client.service;

import com.jafarov.quiz.entity.Question;
import com.jafarov.quiz.entity.QuizEntity;
import com.jafarov.quiz.repository.QuizRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private final QuizRepository quizRepository;

    public ClientService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    // Bütün quizləri qaytarır
    public List<QuizEntity> getAvailableQuizzes() {
        return quizRepository.findAll(); // İstəyə görə filter əlavə edə bilərsən
    }

    // Verilən id-li quiz qaytarır
    public QuizEntity getQuizById(Long id) {
        return quizRepository.findById(id).orElse(null);
    }
}
