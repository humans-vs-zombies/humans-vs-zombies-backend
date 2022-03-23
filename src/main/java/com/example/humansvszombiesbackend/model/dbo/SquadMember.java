package com.example.humansvszombiesbackend.model.dbo;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SquadMember {

    @Id
    private Integer id;

    @OneToOne
    private Game game;

    @OneToOne
    private Squad squad;

    @OneToOne
    private Player player;

}
