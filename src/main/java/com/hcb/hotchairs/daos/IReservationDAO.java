package com.hcb.hotchairs.daos;

import com.hcb.hotchairs.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IReservationDAO extends JpaRepository<Reservation, Long> {
}