package com.jafarov.quiz.repository;

import com.jafarov.quiz.entity.ParticipantAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantAnswerRepository extends JpaRepository<ParticipantAnswer, Long> {

    List<ParticipantAnswer> findByQuizResultId(Long quizResultId);

}
