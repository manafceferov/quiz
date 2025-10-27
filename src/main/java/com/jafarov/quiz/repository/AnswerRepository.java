package com.jafarov.quiz.repository;

import com.jafarov.quiz.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    List<Answer> findByQuestionId(Long questionId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "DELETE FROM answers WHERE question_id = :questionId AND id NOT IN (:ids)", nativeQuery = true)
    void deleteByIds(@Param("questionId") Long questionId, @Param("ids") List<Long> answerIds);

    @Modifying
    @Query("UPDATE Answer a SET a.isActive = false WHERE a.question.topicId = :topicId")
    void deactivateByTopicId(@Param("topicId") Long topicId);

    @Modifying
    @Query("UPDATE Answer a SET a.isActive = :status WHERE a.id = :id")
    void changeStatus(@Param("id") Long id, @Param("status") Boolean status);

    @Query("SELECT a FROM Answer a WHERE a.id = :id")
    Answer findAnswerById(@Param("id") Long id);

    @Query(value = """
            SELECT 
              case 
              when count(*) >= 2 then true else false end as result
            FROM answers a
            WHERE a.question_id = :questionId
              AND a.is_active = true
            """, nativeQuery = true)
    Boolean getExsistTwoIsActiveAnswerByQuestionId(@Param("questionId") Long questionId);

    @Query("SELECT a FROM Answer a JOIN FETCH a.question q JOIN FETCH q.topic WHERE a.question.topic.id = :topicId AND a.isCorrect = true")
    List<Answer> findCorrectAnswersByTopicId(@Param("topicId") Long topicId);

}

