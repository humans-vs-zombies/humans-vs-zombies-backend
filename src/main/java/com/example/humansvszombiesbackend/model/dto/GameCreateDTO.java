package com.example.humansvszombiesbackend.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class GameCreateDTO {


    private String name;

    private Integer participants = 25;

    private Date dateFrom;

    private Date dateTo;

    private String description;

}
