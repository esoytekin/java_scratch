package com.betbull.futbolapi.repository;

import com.betbull.futbolapi.model.LeagueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LeagueRepository extends JpaRepository<LeagueEntity, Long>, LeagueRepositoryCustom, JpaSpecificationExecutor<LeagueEntity> {
}
