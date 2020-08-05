package com.hcb.hotchairs.services.impl;

import com.hcb.hotchairs.converters.CommentConverter;
import com.hcb.hotchairs.daos.ICommentDAO;
import com.hcb.hotchairs.dtos.CommentDTO;
import com.hcb.hotchairs.services.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService implements ICommentService {

    private final ICommentDAO commentDAO;
    private final CommentConverter commentConverter;

    @Autowired
    public CommentService(ICommentDAO commentDAO, CommentConverter commentConverter){
        this.commentDAO = commentDAO;
        this.commentConverter = commentConverter;
    }

    @Override
    public List<CommentDTO> getAll(){
        return commentDAO.findAll().stream().map(commentConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<CommentDTO> getAllByUserId(Long userId){
        return commentDAO.findAllByUserId(userId).stream().map(commentConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public CommentDTO getByReservationId(Long reservationId) {
        return commentConverter.toDTO(commentDAO.findFirstByReservationId(reservationId));
    }

    @Override
    public CommentDTO saveCommentByReservationId(Long reservationId, Long userId, String text) {
        CommentDTO commentDTO = new CommentDTO(0L, userId, reservationId, null, text);
        return commentConverter.toDTO(commentDAO.save(commentConverter.fromDTO(commentDTO)));
    }

    @Override
    public CommentDTO getById(Long id){
        return commentConverter.toDTO(commentDAO.findById(id).orElse(null));
    }

    @Override
    public List<CommentDTO> getAllByPlaceId(Long placeId) {
        return commentDAO.findAllByReservationPlaceId(placeId)
                .stream()
                .map(commentConverter::toDTO)
                .collect(Collectors.toList());
    }

}