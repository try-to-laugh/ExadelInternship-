package com.hcb.hotchairs.daos;

import com.hcb.hotchairs.entities.Detail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDetailDAO extends JpaRepository<Detail, Long>{

    @Query("FROM Detail " +
            "WHERE reservation.id = ?1 AND (date > CURRENT_DATE OR " +
            "(date = CURRENT_DATE AND reservation.endTime > CURRENT_TIME)) " +
            "ORDER BY date ASC")
    List<Detail> findByReservationId(Long reservationId);

    //Некит, я тоже это юзаю не меня ничего пажойжда
    @Query("FROM Detail " +
            "WHERE reservation.user.id = ?1 AND (date > CURRENT_DATE OR " +
            "(date = CURRENT_DATE AND reservation.endTime > CURRENT_TIME)) " +
            "ORDER BY date ASC, reservation.startTime ASC")
    List<Detail> findByUserId(Long userId);
}