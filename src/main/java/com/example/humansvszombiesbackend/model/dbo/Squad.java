package com.example.humansvszombiesbackend.model.dbo;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Squad {

    @Id
    @GeneratedValue
    private Integer id;

    @Column
    private String name;

    @Column
    private Boolean isHuman;

    @OneToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @OneToMany(mappedBy = "squad", cascade = {CascadeType.REMOVE})
    private List<SquadMember> squadMembers;

}
