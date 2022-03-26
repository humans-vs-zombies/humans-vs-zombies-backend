package com.example.humansvszombiesbackend.model.dbo;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
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
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Game game;

    @Builder.Default
    @OneToMany(mappedBy = "squad", cascade = CascadeType.ALL)
    private List<SquadMember> squadMembers = new ArrayList<>();

}
