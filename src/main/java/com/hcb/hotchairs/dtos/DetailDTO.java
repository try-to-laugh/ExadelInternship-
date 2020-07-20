package com.hcb.hotchairs.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
public class DetailDTO {

    private Long id;
    private Long reservationId;
    private Date date;
}
