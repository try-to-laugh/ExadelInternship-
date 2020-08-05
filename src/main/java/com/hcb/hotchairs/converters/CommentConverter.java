package com.hcb.hotchairs.converters;

import com.hcb.hotchairs.dtos.CommentDTO;
import com.hcb.hotchairs.entities.Comment;
import com.hcb.hotchairs.entities.Reservation;
import com.hcb.hotchairs.entities.User;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CommentConverter {
    public CommentDTO toDTO(Comment comment){
        if(Objects.isNull(comment)){
            return null;
        }

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setReservationId(comment.getReservation().getId());
        commentDTO.setUserId(comment.getUser().getId());
        commentDTO.setTimestamp(comment.getTimestamp());
        commentDTO.setText(comment.getText());

        return commentDTO;
    }

    public Comment fromDTO(CommentDTO commentDTO) {
        if (Objects.isNull(commentDTO)) {
            return null;
        }

        User author = new User();
        Reservation reservation = new Reservation();
        author.setId(commentDTO.getUserId());
        reservation.setId(commentDTO.getReservationId());

        return new Comment(commentDTO.getId(), author, commentDTO.getTimestamp(), commentDTO.getText(), reservation);
    }
}
