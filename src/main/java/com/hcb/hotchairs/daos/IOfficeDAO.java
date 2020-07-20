package com.hcb.hotchairs.daos;

import com.hcb.hotchairs.entities.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOfficeDAO extends JpaRepository<Office, Long> {

}
