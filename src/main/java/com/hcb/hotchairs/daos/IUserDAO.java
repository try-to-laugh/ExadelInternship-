package com.hcb.hotchairs.daos;

import com.hcb.hotchairs.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserDAO extends JpaRepository<User, Long> {

    User findByEmail(String email);

    @Query("SELECT user FROM User user WHERE user.hr.id = :hr_id")
    List<User> finByHrId(@Param("hr_id")Long hrId);
}
