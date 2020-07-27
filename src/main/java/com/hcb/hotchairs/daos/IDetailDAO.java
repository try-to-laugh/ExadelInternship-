package com.hcb.hotchairs.daos;

import com.hcb.hotchairs.entities.Detail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDetailDAO extends JpaRepository<Detail, Long>{
    @Query("FROM Detail WHERE reservation.id = ?1 ORDER BY date ASC")
    List<Detail> findByReservationId(Long reservationId);
}