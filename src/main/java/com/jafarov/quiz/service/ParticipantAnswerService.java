package com.jafarov.quiz.service;

import com.jafarov.quiz.entity.ParticipantAnswer;
import com.jafarov.quiz.repository.ParticipantAnswerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipantAnswerService {

    private final ParticipantAnswerRepository participantAnswerRepository;

    public ParticipantAnswerService(ParticipantAnswerRepository participantAnswerRepository
    ) {
        this.participantAnswerRepository = participantAnswerRepository;
    }

    @Transactional
    public void saveAll(List<ParticipantAnswer> answers) {
        participantAnswerRepository.saveAll(answers);
    }
    public List<ParticipantAnswer> findByQuizResultId(Long quizResultId) {
        return participantAnswerRepository.findByQuizResultId(quizResultId);
    }
}
