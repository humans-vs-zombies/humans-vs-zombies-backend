package com.example.humansvszombiesbackend.model.dbo;

import com.example.humansvszombiesbackend.enums.GameState;
import com.example.humansvszombiesbackend.model.dto.PlayerDTO;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Game {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(length = 40, nullable = false)
    private String name;

    @Builder.Default
    @Column(nullable = false)
    private GameState state = GameState.CONFIGURATION;

    @Builder.Default
    @Column(nullable = false)
    private Integer participants = 25;

    @Column(nullable = false)
    private Date dateFrom;

    @Column(nullable = false)
    private Date dateTo;

    @JsonIgnore
    @OneToMany
    @JoinColumn(nullable = false)
    private Set<Player> players = new LinkedHashSet<>();

    @Builder.Default
    @Column
    private String description = null;
  
    @JsonGetter
    public Set<PlayerDTO> players()
    {
        return players.stream().map(p -> PlayerDTO.builder()
                .id(p.getId())
                .isHuman(p.isHuman())
                .userId(p.getUserId())
                .build()
        ).collect(Collectors.toSet());
    }

}
