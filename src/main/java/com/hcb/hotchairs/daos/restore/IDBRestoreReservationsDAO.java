package com.hcb.hotchairs.daos.restore;

import com.hcb.hotchairs.entities.Reservation;
import com.hcb.hotchairs.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface IDBRestoreReservationsDAO extends JpaRepository<Reservation, Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Reservation")
    void clearReservationTable();
}
