package com.quiz.repository;

import com.quiz.entity.Topic;
import com.quiz.dto.topic.TopicWithQuestionCountProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    Page<Topic> findByNameContainingIgnoreCase(String name, Pageable pageable);

    @Modifying
    @Query("UPDATE Topic t SET t.isActive = :status WHERE t.id = :id")
    void changeStatus(@Param("id") Long id, @Param("status") Boolean status);

    @Query(value = """
                Select t.id,
                       t.name,
                       q.count as questionCount
                from topics t
                         inner join (
                    Select q.topic_id, count(q.id) as count from questions q where q.is_active = true group by topic_id
                ) q on t.id = q.topic_id
                where t.is_active = true
            """,
            countQuery = """
                    SELECT COUNT(*) as questionCount FROM (
                        SELECT t.id
                        FROM topics t
                            inner join (
                            Select q.topic_id, count(q.id) as count from questions q where q.is_active = true group by topic_id
                        ) q on t.id = q.topic_id
                    where t.is_active = true
                    )
                    AS count_sub
                    """, nativeQuery = true)
    Page<TopicWithQuestionCountProjection> getAllTopicsWithQuestionsCount(Pageable pageable);

    @Query("SELECT t FROM Topic t WHERE t.isActive = true")
    Page<Topic> findAllByIsActiveTrue(Pageable pageable);

    @Query(
            value = """
        SELECT t.id AS id, t.name AS name, COUNT(q.id) AS questionCount
        FROM Topic t
        LEFT JOIN Question q ON q.topic.id = t.id AND q.isActive = true
        WHERE t.isActive = true
          AND (:keyword IS NULL OR LOWER(t.name) LIKE LOWER(CONCAT('%', :keyword, '%')))
        GROUP BY t.id, t.name
    """,
            countQuery = """
        SELECT COUNT(t.id)
        FROM Topic t
        WHERE t.isActive = true
          AND (:keyword IS NULL OR LOWER(t.name) LIKE LOWER(CONCAT('%', :keyword, '%')))
    """
    )
    Page<TopicWithQuestionCountProjection> searchByNameWithQuestionCount(
            @Param("keyword") String keyword,
            Pageable pageable
    );


    Page<Topic> findByByParticipant(Long byParticipant, Pageable pageable);

    Page<Topic> findByByParticipantAndNameContainingIgnoreCase(Long byParticipant, String name, Pageable pageable);

    @Query("""
                SELECT t FROM Topic t
                LEFT JOIN t.participant p
                WHERE (:keyword IS NULL OR LOWER(t.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
                   OR LOWER(p.firstName) LIKE LOWER(CONCAT('%', :keyword, '%'))
                   OR LOWER(p.lastName) LIKE LOWER(CONCAT('%', :keyword, '%'))
                   OR LOWER(p.fatherName) LIKE LOWER(CONCAT('%', :keyword, '%')))
            """)
    Page<Topic> searchByNameOrParticipant(String keyword, Pageable pageable);

}