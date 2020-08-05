package com.hcb.hotchairs.services;

import com.hcb.hotchairs.dtos.CommentDTO;

import java.util.List;

public interface ICommentService {
    List<CommentDTO> getAll();
    List<CommentDTO> getAllByUserId(Long userId);
    CommentDTO getByReservationId(Long reservationId);
    CommentDTO saveCommentByReservationId(Long reservationId, Long userId, String text);
    CommentDTO getById(Long id);

    List<CommentDTO> getAllByPlaceId(Long placeId);
}
