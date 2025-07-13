package com.jafarov.quiz.admin.repository;

import com.jafarov.quiz.admin.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizTopicRepository extends JpaRepository<Topic, Long> {


}