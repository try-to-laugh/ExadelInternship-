package com.hcb.hotchairs.daos;

import com.hcb.hotchairs.entities.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface IOfficeDAO extends JpaRepository<Office, Long> {

    @Query("FROM Office WHERE city.id = ?1")
    List<Office> findAllByCityId(Long cityId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Office WHERE id = ?1")
    void deleteOfficeById(Long id);
}
