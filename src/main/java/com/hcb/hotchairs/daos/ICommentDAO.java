package com.hcb.hotchairs.daos;

import com.hcb.hotchairs.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICommentDAO extends JpaRepository<Comment, Long> {

    @Query("FROM Comment WHERE user.id = ?1")
    List<Comment> findAllByUserId(Long userId);

    Comment findFirstByReservationId(Long reservationId);
    List<Comment> findAllByReservationPlaceId(Long placeId);
}
