package com.hcb.hotchairs.daos;

import com.hcb.hotchairs.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserDAO extends JpaRepository<User, Long> {

}
