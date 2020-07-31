package com.hcb.hotchairs.daos;

import com.hcb.hotchairs.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserDAO extends JpaRepository<User, Long> {

    User findByEmail(String email);

    @Query("SELECT u FROM User u WHERE lower(u.name) LIKE lower(CONCAT('%', :username, '%'))")
    List<User> findAllByNameContains(String username, Pageable pageable);

    @Query("SELECT user FROM User user WHERE user.hr.id = :hr_id")
    List<User> finByHrId(@Param("hr_id")Long hrId);

    @Query("SELECT u FROM User u WHERE lower(u.name) LIKE lower(CONCAT('%', :name, '%')) " +
            "OR lower(u.email) LIKE lower(CONCAT('%', :email, '%'))")
    List<User> findAllByNameContainsOrEmailContains(String name, String email);

    @Query("SELECT COUNT(u) FROM User u WHERE lower(u.name) LIKE lower(CONCAT('%', :username, '%'))")
    Long countAllByNameContains(String username);
}
