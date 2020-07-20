package com.hcb.hotchairs.converters;

import com.hcb.hotchairs.dtos.CommentDTO;
import com.hcb.hotchairs.entities.Comment;
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
        commentDTO.setPlaceId(comment.getPlace().getId());
        commentDTO.setUserId(comment.getUser().getId());
        commentDTO.setTimestamp(comment.getTimestamp());
        commentDTO.setText(comment.getText());

        return commentDTO;
    }
}
