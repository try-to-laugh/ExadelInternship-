package com.hcb.hotchairs.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class CommentDTO {
    private Long id;
    private Long userId;
    private Long placeId;
    private Timestamp timestamp;
    private String text;
}
