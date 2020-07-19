package com.hcb.hotchairs.daos;

import com.hcb.hotchairs.entities.Floor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFloorDAO extends JpaRepository<Floor,Long> {

}
