package com.hcb.hotchairs.daos;

import com.hcb.hotchairs.entities.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPlaceDAO extends JpaRepository<Place, Long> {

}
