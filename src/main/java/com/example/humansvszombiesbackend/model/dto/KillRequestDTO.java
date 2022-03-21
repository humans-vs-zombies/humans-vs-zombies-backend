package com.example.humansvszombiesbackend.model.dto;

import lombok.Data;

@Data
public class KillRequestDTO {

    private String biteCode;

    private Integer victimId;

    private Integer killerId;

}
