package com.hcb.hotchairs.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private Long id;
    private Long userId;
    private Long reservationId;
    private Timestamp timestamp;
    private String text;
}
