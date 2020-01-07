package com.betbull.futbolapi.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "league")
public class LeagueEntity {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private TeamEntity team;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private PlayerEntity player;

    private LocalDate startDate;

    private LocalDate endDate;

}
