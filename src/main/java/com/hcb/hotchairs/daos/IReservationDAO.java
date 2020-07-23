package com.hcb.hotchairs.daos;

import com.hcb.hotchairs.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface IReservationDAO extends JpaRepository<Reservation, Long> {

    @Query("select res from Reservation res join res.details det where  det.date = ?1 and res.place.floor.id = ?2")
    List<Reservation> findAllByDateAndFloor(Date date, Long floorId);

    @Query("select res from Reservation res join res.details det where  det.date = ?1 and res.place.floor.office.id = ?2")
    List<Reservation> findAllByDateAndOffice(Date date, Long officeId);
}