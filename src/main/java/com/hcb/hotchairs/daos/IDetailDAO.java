package com.hcb.hotchairs.daos;

import com.hcb.hotchairs.entities.Detail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDetailDAO extends JpaRepository<Detail, Long>{

    @Query("FROM Detail " +
            "WHERE reservation.id = ?1 AND date >= CURRENT_DATE " +
            "ORDER BY date ASC")
    List<Detail> findByReservationId(Long reservationId);

    @Query("FROM Detail " +
            "WHERE reservation.user.id = ?1 AND date >= CURRENT_DATE AND reservation.startTime > CURRENT_TIME " +
            "ORDER BY date ASC, reservation.startTime ASC")
    List<Detail> findByUserId(Long userId);
}