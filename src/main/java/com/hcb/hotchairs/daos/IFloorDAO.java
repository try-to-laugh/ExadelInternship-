package com.hcb.hotchairs.daos;

import com.hcb.hotchairs.entities.Floor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface IFloorDAO extends JpaRepository<Floor,Long> {

    List<Floor> findAllByOfficeId(Long officeId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Floor WHERE id = ?1")
    void deleteFloorById(Long id);
}
