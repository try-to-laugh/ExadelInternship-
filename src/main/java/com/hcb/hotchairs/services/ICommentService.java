package com.hcb.hotchairs.services;

import com.hcb.hotchairs.dtos.CommentDTO;

import java.util.List;

public interface ICommentService {
    List<CommentDTO> getAll();
    List<CommentDTO> getAllByUserId(Long userId);
    List<CommentDTO> getAllByPlaceId(Long placeId);
    CommentDTO getById(Long id);

}
