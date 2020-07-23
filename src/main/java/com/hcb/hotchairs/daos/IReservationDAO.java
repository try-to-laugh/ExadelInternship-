package com.hcb.hotchairs.daos;

import com.hcb.hotchairs.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IReservationDAO extends JpaRepository<Reservation, Long> {

    @Query("FROM Reservation WHERE user.id = ?1")
    List<Reservation> findAllByUserId(Long userId);
}