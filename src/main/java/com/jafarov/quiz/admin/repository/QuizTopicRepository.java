package com.jafarov.quiz.admin.repository;

import com.jafarov.quiz.admin.entity.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizTopicRepository extends JpaRepository<Topic, Long> {


    Page<Topic> findByNameContainingIgnoreCase(String name, Pageable pageable);

    @Modifying
    @Query("UPDATE Topic t SET t.isActive = :status WHERE t.id = :id")
    void changeStatus(@Param("id") Long id,@Param("status") Boolean status);
}