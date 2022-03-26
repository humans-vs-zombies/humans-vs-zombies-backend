package com.example.humansvszombiesbackend.model.dbo;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Kill {

    @Id
    @GeneratedValue
    private Integer id;

    @CreationTimestamp
    private Date timeOfDeath;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @NotNull
    @OneToOne(optional = false)
    @JoinColumn(name = "victim_id", nullable = false, unique = true)
    private Player victim;

    @NotNull
    @OneToOne(optional = false)
    @JoinColumn(name = "killer_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Player killer;

}
