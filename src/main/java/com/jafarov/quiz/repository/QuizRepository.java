package com.jafarov.quiz.repository;

import com.jafarov.quiz.entity.QuizEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  // Bu annotasiya olmadan da olur, amma əlavə etsən yaxşıdır
public interface QuizRepository extends JpaRepository<QuizEntity, Long> {
}
