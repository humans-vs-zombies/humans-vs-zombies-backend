package com.example.humansvszombiesbackend.model.dbo;

import com.example.humansvszombiesbackend.model.dto.PlayerDTO;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Chat {

    @Id
    @GeneratedValue
    private Integer id;

    @Column
    private String message;

    @Column
    private Boolean isHumanGlobal;

    @Column
    private Boolean isZombieGlobal;

    @CreationTimestamp
    private Date chatTime;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "squad_id")
    private Squad squad;

    @JsonGetter
    public PlayerDTO player() {
        return PlayerDTO.from(player);
    }

}
