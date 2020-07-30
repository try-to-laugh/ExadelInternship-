package com.hcb.hotchairs.daos;

import com.hcb.hotchairs.entities.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface IPlaceDAO extends JpaRepository<Place, Long> {

    @Query("FROM Place p WHERE p.floor.id = ?1")
    List<Place> findAllByFloorId(Long floorId);

    @Query("FROM Place p WHERE p.floor.office.id = ?1")
    List<Place> findAllByOfficeId(Long officeId);


    @Query(value = "SELECT p FROM Place p WHERE p.floor.id = :floorId AND p.id NOT IN :closed")
    List<Place> findFreePlaceOnFloor(@Param("closed") Collection<Long> identifiers,
                                     @Param("floorId") Long floorId);

    @Query(value = "SELECT p FROM Place p WHERE p.floor.office.id = :officeId AND p.id NOT IN :closed")
    List<Place> findFreePlaceOnOffice(@Param("closed") Collection<Long> identifiers,
                                     @Param("officeId") Long floorId);
}
