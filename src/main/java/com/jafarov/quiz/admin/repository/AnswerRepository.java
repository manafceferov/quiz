package com.jafarov.quiz.admin.repository;

import com.jafarov.quiz.admin.entity.Answer;
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

}
