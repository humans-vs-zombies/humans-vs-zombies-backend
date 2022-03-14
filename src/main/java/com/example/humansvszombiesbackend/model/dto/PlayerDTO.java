package com.example.humansvszombiesbackend.model.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PlayerDTO {

    private Integer id;

    private boolean isHuman;

    private String biteCode;

}
