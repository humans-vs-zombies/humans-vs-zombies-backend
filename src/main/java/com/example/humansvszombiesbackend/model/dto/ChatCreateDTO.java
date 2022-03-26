package com.example.humansvszombiesbackend.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatCreateDTO {

    private int playerId;

    private String message;

}

