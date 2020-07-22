package com.hcb.hotchairs.daos;

import com.hcb.hotchairs.entities.City;
import com.hcb.hotchairs.entities.Floor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFloorDAO extends JpaRepository<Floor,Long> {

    List<Floor> findAllByOfficeId(Long officeId);
}
