package com.example.humansvszombiesbackend.model.dbo;

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
    private boolean isHuman = true;

    @Column
    private String biteCode;

    @ManyToOne
    @JoinColumn
    private Game currentGame;

    @Column(nullable = false)
    private UUID userId;

}
