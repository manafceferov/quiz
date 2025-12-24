package com.quiz.repository;

import com.quiz.entity.Question;
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

    @EntityGraph(attributePaths = {"answers"})
    @Query("SELECT q FROM Question q WHERE q.id = :id ")
    Question getQuestionWithAnswersById(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Question q SET q.isActive = :status WHERE q.id = :id")
    void changeStatus(@Param("id") Long id, @Param("status") Boolean status);

    @Modifying
    @Query("UPDATE Question q SET q.isActive = false WHERE q.topicId = :topicId")
    void deactivateByTopicId(@Param("topicId") Long topicId);

    @Modifying
    @Query(value = """
                UPDATE questions q
                SET is_active = CASE 
                    WHEN (
                        SELECT COUNT(*) 
                        FROM answers a 
                        WHERE a.question_id = q.id 
                        AND a.is_active = true
                    ) >= 2 THEN true
                    ELSE false
                END
                WHERE q.id = :questionId
            """, nativeQuery = true)
    void deactivateIfAnyAnswerIsInactive(@Param("questionId") Long questionId);

    @Query("select count(*) from Question q where q.topicId = :topicId and q.isActive = true")
    Long getCountByTopicId(Long topicId);

}
