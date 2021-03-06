package com.example.humansvszombiesbackend.model.dbo;

import com.example.humansvszombiesbackend.enums.GameState;
import com.example.humansvszombiesbackend.model.dto.PlayerDTO;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    @NotBlank
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
    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(nullable = false)
    private Set<Player> players = new LinkedHashSet<>();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder.Default
    @Column
    private String description = null;
  
    @JsonGetter
    public Set<PlayerDTO> players()
    {
        return players.stream().map(PlayerDTO::from).collect(Collectors.toSet());
    }

}
