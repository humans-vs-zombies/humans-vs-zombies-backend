package com.example.humansvszombiesbackend.model.dbo;

import com.example.humansvszombiesbackend.enums.GameState;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(length = 40, nullable = false)
    private String name;

    @Column(nullable = false)
    private GameState state = GameState.CONFIGURATION;
}
