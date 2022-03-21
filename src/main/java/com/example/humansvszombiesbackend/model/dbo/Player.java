package com.example.humansvszombiesbackend.model.dbo;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Player {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    @Builder.Default
    private boolean isHuman = true;

    @Column
    private String biteCode;

    @JsonIgnore
    @ManyToOne
    @JoinColumn
    private Game currentGame;

    @Column(nullable = false)
    private UUID userId;

    //TODO: map to DTO
    @JsonGetter
    public Game currentGame() {
        return currentGame;
    }

}
