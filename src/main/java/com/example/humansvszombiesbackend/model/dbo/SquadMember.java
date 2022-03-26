package com.example.humansvszombiesbackend.model.dbo;

import com.example.humansvszombiesbackend.model.dto.PlayerDTO;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SquadMember {

    @Id
    @GeneratedValue
    private Integer id;

    @JsonIgnore
    @OneToOne
    private Game game;

    @JsonIgnore
    @ManyToOne
    private Squad squad;

    @JsonIgnore
    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Player player;

    @JsonGetter
    public PlayerDTO player() {
        return PlayerDTO.from(player);
    }


}
