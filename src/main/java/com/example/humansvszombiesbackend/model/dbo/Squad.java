package com.example.humansvszombiesbackend.model.dbo;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Squad {

    @Id
    private Integer id;

    @Column
    private String name;

    @Column
    private Boolean isHuman;

    @OneToOne
    private Game game;

}
