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
    private Player player;

    @JsonGetter
    public PlayerDTO player() {
        return PlayerDTO.from(player);
    }


}
