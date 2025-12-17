package com.jafarov.quiz.repository;

import com.jafarov.quiz.entity.QuizResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizResultRepository extends JpaRepository<QuizResult, Long> {

    Page<QuizResult> findAllByParticipantId(Long participantId, Pageable pageable);

}

