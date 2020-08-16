package com.hcb.hotchairs.daos;

import com.hcb.hotchairs.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Time;
import java.util.Collection;
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
    List<Reservation> findByTimeDateAndPlace(Date date,
                                             Time startTime,
                                             Time endTime,
                                             Long placeId);

    @Query("FROM Reservation " +
            " WHERE user.id = ?1 AND (endDate > CURRENT_DATE OR " +
            " (endDate = CURRENT_DATE AND endTime > CURRENT_TIME)) " +
            " ORDER BY startDate ASC, startTime ASC")
    List<Reservation> findByUserId(Long userId);

    @Query("FROM Reservation " +
            "WHERE place.floor.id = ?1 AND (endDate > CURRENT_DATE OR " +
            "(endDate = CURRENT_DATE AND endTime > CURRENT_TIME))")
    List<Reservation> findRelevantReservationsByFloorId(Long floorId);

    @Query("FROM Reservation " +
            "WHERE place.floor.office.id = ?1 AND (endDate > CURRENT_DATE OR " +
            "(endDate = CURRENT_DATE AND endTime > CURRENT_TIME))")
    List<Reservation> findRelevantReservationsByOfficeId(Long officeId);

    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.place.id = :placeId " +
            "AND (r.endDate > CURRENT_DATE OR (r.endDate = CURRENT_DATE AND r.endTime > CURRENT_TIME))")
    Long isPlaceReserved(@Param("placeId") Long placeId);

    @Query("FROM Reservation res WHERE " +
            " res.startDate <= ?2 " +
            " AND res.endDate >= ?1 " +
            " AND res.startTime < ?4 " +
            " AND res.endTime > ?3 " +
            " AND res.user.id = ?5 ")
    List<Reservation> findIntersectionForUser(Date startDate, Date endDate,
                                              Time startTime, Time endTime,
                                              Long userId);

    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.place.floor.id = :floorId AND r.place.id NOT IN :placesId AND " +
            "(r.endDate > CURRENT_DATE OR (r.endDate = CURRENT_DATE AND r.endTime > CURRENT_TIME))")
    Long checkForDeletingViolation(@Param("placesId") Collection<Long> placesIds, @Param("floorId") Long floorId);

    @Query("FROM Reservation " +
            "WHERE user.hr.id = ?1 AND (endDate > CURRENT_DATE OR " +
            "(endDate = CURRENT_DATE AND endTime > CURRENT_TIME)) " +
            "ORDER BY startDate ASC, startTime ASC")
    List<Reservation> findByHrId(Long userId);

    @Modifying
    @Query("DELETE FROM Reservation WHERE id = ?1 ")
    void deleteReservation(Long reservationId);

    @Query("FROM Reservation res WHERE res.host.id = ?1 AND res.user.id = ?2 ")
    Optional<Reservation> findByHostAnUser(Long hostId, Long userId);

    @Query("FROM Reservation res WHERE res.host.id = ?1 ")
    List<Reservation> findAllByHost(Long hostReservationId);

    @Query("FROM Reservation res WHERE res.place.id = :placeId " +
            " AND res.endDate >= :startDate " +
            " AND res.startDate <= :endDate " +
            " AND res.startTime <= :endTime " +
            " AND res.endTime >= :startTime ")
    List<Reservation> findAllByPlaceInDateTimeRange(@Param("placeId") Long placeId,
                                                    @Param("startDate") Date startDate,
                                                    @Param("endDate") Date endDate,
                                                    @Param("startTime") Time startTime,
                                                    @Param("endTime") Time endTime);
}