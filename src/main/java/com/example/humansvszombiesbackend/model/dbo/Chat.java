package com.example.humansvszombiesbackend.model.dbo;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Chat {

    @Id
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
