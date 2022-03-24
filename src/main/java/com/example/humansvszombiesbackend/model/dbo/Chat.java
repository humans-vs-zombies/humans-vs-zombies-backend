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

    @OneToOne
    private Game game;

    @OneToOne
    private Player player;

    @OneToOne
    private Squad squad;

}
