package com.example.humansvszombiesbackend.model.dbo;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SquadCheckIn {

    @Id
    @GeneratedValue
    private Integer id;

    @Column
    private Date startTime;

    @Column
    private Date endTime;

//    @Column
//    private Double lat;
//
//    @Column
//    private Double lng;

    @OneToOne
    private Game game;

    @OneToOne
    private Squad squad;

    @OneToOne
    private SquadMember squadMember;

}
