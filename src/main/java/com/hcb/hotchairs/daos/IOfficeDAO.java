package com.hcb.hotchairs.daos;

import com.hcb.hotchairs.entities.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOfficeDAO extends JpaRepository<Office, Long> {

    @Query("FROM Office WHERE City.id = ?1")
    List<Office> findAllByCityId(Long cityId);
}
