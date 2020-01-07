package com.betbull.futbolapi.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "team")
public class TeamEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @OneToMany(mappedBy = "team")
    List<LeagueEntity> leagueEntities;

}
