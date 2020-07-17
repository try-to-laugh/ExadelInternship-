package com.hcb.hotchairs.daos;

import com.hcb.hotchairs.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ILoginDAO extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
