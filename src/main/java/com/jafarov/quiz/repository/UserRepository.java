package com.jafarov.quiz.repository;

import com.jafarov.quiz.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    Optional<User> findByEmail(String email);

    @EntityGraph(attributePaths = {"attachment"})
    Page<User> findAll(Pageable pageable);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.attachment WHERE u.id = :id")
    User getUserByIdWithAttachment(@Param("id") Long id);

    @Query("SELECT u FROM User u WHERE " + "LOWER(CONCAT(u.firstName, ' ', u.lastName, ' ', u.fatherName)) " +
           "LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<User> searchByFullName(@Param("name") String name, Pageable pageable);

    @Modifying
    @Query("UPDATE User u SET u.status = :status WHERE u.id = :id")
    void changeStatus(@Param("id") Long id,@Param("status") Boolean status);
}
