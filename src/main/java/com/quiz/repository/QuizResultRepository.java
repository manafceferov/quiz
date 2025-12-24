package com.quiz.repository;

import com.quiz.entity.QuizResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizResultRepository extends JpaRepository<QuizResult, Long> {

    Page<QuizResult> findAllByParticipantId(Long participantId, Pageable pageable);

    @Query(
            value = """
                SELECT qr
                FROM QuizResult qr
                JOIN qr.topic t
                WHERE qr.participantId = :participantId
                  AND (:keyword IS NULL 
                       OR LOWER(t.name) LIKE LOWER(CONCAT('%', :keyword, '%')))
            """,
            countQuery = """
                SELECT COUNT(qr)
                FROM QuizResult qr
                JOIN qr.topic t
                WHERE qr.participantId = :participantId
                  AND (:keyword IS NULL 
                       OR LOWER(t.name) LIKE LOWER(CONCAT('%', :keyword, '%')))
            """
    )
    Page<QuizResult> searchByParticipantAndTopic(
            @Param("participantId") Long participantId,
            @Param("keyword") String keyword,
            Pageable pageable
    );

}

