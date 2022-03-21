package com.example.humansvszombiesbackend.model.dto;

import com.example.humansvszombiesbackend.model.dbo.Player;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class PlayerDTO {

    private Integer id;

    private String name;

    private boolean isHuman;

    private UUID userId;

    public static PlayerDTO from(Player player) {
        return builder()
                .id(player.getId())
                .name(player.getName())
                .isHuman(player.isHuman())
                .userId(player.getUserId())
                .build();
    }

}
