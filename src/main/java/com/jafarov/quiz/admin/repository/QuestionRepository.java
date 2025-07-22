package com.jafarov.quiz.admin.repository;

import com.jafarov.quiz.admin.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("SELECT q FROM Question q WHERE q.topicId = :topicId Order By q.id")
    Page<Question> findByTopicId(@Param("topicId") Long topicId, Pageable pageable);

    @Query("SELECT q FROM Question q WHERE q.topicId = :topicId AND LOWER(q.question) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Question> findByTopicIdAndQuestionContainingIgnoreCase(@Param("topicId") Long topicId,
                                                                @Param("keyword") String keyword,
                                                                Pageable pageable);


    @EntityGraph(attributePaths={"answers"})
    @Query("SELECT q FROM Question q WHERE q.id = :id ")
    Question getQuestionWithAnswersById(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Question q SET q.isActive = :status WHERE q.id = :id")
    void changeStatus(@Param("id") Long id, @Param("status") Boolean status);
}
