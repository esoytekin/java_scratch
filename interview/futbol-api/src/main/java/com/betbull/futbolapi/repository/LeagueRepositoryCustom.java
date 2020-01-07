package com.betbull.futbolapi.repository;

import com.betbull.futbolapi.model.LeagueEntity;
import com.betbull.futbolapi.model.PlayerEntity;

import java.util.List;

public interface LeagueRepositoryCustom {
    List<LeagueEntity> findLeaguesByPlayer(PlayerEntity player);
    List<LeagueEntity> findLeaguesByTeamAndYear(Long teamId, int year);
}
