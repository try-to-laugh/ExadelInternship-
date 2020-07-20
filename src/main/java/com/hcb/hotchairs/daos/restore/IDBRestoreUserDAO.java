package com.hcb.hotchairs.daos.restore;

import com.hcb.hotchairs.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface IDBRestoreUserDAO extends JpaRepository<User, Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM User")
    void clearUserTable();
}
