package com.betbull.futbolapi.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "player")
public class PlayerEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String surname;

    private LocalDate birthDate;

    @OneToMany(mappedBy = "player")
    List<LeagueEntity> leagueEntities;
}
