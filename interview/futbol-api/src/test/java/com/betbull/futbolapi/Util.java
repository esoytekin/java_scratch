package com.betbull.futbolapi;

import com.betbull.futbolapi.model.LeagueEntity;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.util.Random;

@UtilityClass
public class Util {
     public LeagueEntity createLeagueEntity(String startDate, String endDate){
        LeagueEntity leagueEntity = new LeagueEntity();
        leagueEntity.setStartDate(LocalDate.parse(startDate));
        leagueEntity.setEndDate(LocalDate.parse(endDate));
        leagueEntity.setId(new Random().nextLong());
        return leagueEntity;
    }
}
