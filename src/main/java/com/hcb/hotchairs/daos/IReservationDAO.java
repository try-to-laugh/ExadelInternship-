package com.hcb.hotchairs.daos;

import com.hcb.hotchairs.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

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


    @Query("SELECT res FROM Reservation res JOIN res.details det WHERE" +
            " det.date = ?1" +
            " AND res.startTime < ?3" +
            " AND res.endTime > ?2 " +
            " AND res.place.id = ?4")
    Optional<Reservation> findByTimeDateAndPlace(Date date,
                                                Time startTime,
                                                Time endTime,
                                                Long placeId);

    @Query("FROM Reservation " +
            "WHERE user.id = ?1 AND (endDate > CURRENT_DATE OR " +
            "(endDate = CURRENT_DATE AND endTime > CURRENT_TIME)) " +
            "ORDER BY startDate ASC, startTime ASC")
    List<Reservation> findByUserId(Long userId);

    @Query("FROM Reservation " +
            "WHERE place.floor.id = ?1 AND (endDate > CURRENT_DATE OR " +
            "(endDate = CURRENT_DATE AND endTime > CURRENT_TIME))")
    List<Reservation> findRelevantReservationsByFloorId(Long floorId);

    @Query("FROM Reservation " +
            "WHERE place.floor.office.id = ?1 AND (endDate > CURRENT_DATE OR " +
            "(endDate = CURRENT_DATE AND endTime > CURRENT_TIME))")
    List<Reservation> findRelevantReservationsByOfficeId(Long officeId);

    @Query("FROM Reservation " +
            "WHERE user.hr.id = ?1 AND (endDate > CURRENT_DATE OR " +
            "(endDate = CURRENT_DATE AND endTime > CURRENT_TIME))")
    List<Reservation> findRelevantReservationsByOfficeId(Long officeId);
}