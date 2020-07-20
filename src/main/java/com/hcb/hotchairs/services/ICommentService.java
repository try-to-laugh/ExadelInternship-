package com.hcb.hotchairs.services;

import com.hcb.hotchairs.dtos.CommentDTO;

import java.util.List;

public interface ICommentService {
    List<CommentDTO> getAll();
    List<CommentDTO> getByUserId(Long id);
    CommentDTO getById(Long id);

}
