package com.hcb.hotchairs.daos;

import com.hcb.hotchairs.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Repository
public interface IReservationDAO extends JpaRepository<Reservation, Long> {

    @Query("SELECT res FROM Reservation res JOIN res.details det WHERE" +
            " det.date = ?1" +
            " and res.startTime < ?3" +
            " and res.endTime > ?2" +
            " and res.place.floor.id = ?4")
    List<Reservation> findAllByTimeDateAndFloor(Date date,
                                                Time startTime,
                                                Time endTime,
                                                Long floorId);

    @Query("SELECT res FROM Reservation res JOIN res.details det WHERE" +
            " det.date = ?1" +
            " and res.startTime < ?3" +
            " and res.endTime > ?2" +
            " and res.place.floor.office.id = ?4")
    List<Reservation> findAllByTimeDateAndOffice(Date date,
                                                 Time startTime,
                                                 Time endTime,
                                                 Long officeId);

    @Query("FROM Reservation WHERE user.id = ?1 AND endDate > CURRENT_DATE ORDER BY startDate ASC, startTime ASC")
    /**TODO:
     * Add sort by startTime, if needed
     */
    List<Reservation> findByUserId(Long userId);
}