package com.hcb.hotchairs.daos;

import com.hcb.hotchairs.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleDAO extends JpaRepository<Role, Long> {
}
