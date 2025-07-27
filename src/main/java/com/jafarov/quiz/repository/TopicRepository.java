package com.jafarov.quiz.repository;

import com.jafarov.quiz.entity.Topic;
import com.jafarov.quiz.dto.topic.TopicWithQuestionCountProjection;
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
    void changeStatus(@Param("id") Long id,@Param("status") Boolean status);


    @Query(value = """
                Select t.id,
                       t.name,
                       q.count as question_count
                from quiz_topics t
                         inner join (
                    Select q.topic_id, count(q.id) as count from questions q where q.is_active = true group by topic_id
                ) q on t.id = q.topic_id
                where t.is_active = true
            """,
            countQuery = """
            SELECT COUNT(*) as question_count FROM (
                SELECT t.id
                FROM quiz_topics t 
                    inner join (
                    Select q.topic_id, count(q.id) as count from questions q where q.is_active = true group by topic_id
                ) q on t.id = q.topic_id
            where t.is_active = true
            ) 
            AS count_sub
            """, nativeQuery = true)
    Page<TopicWithQuestionCountProjection> getAllTopicsWithQuestionsCount(Pageable pageable);
}