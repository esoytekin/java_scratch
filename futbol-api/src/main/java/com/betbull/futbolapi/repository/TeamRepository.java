package com.betbull.futbolapi.repository;

import com.betbull.futbolapi.model.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TeamRepository extends JpaRepository<TeamEntity, Long>, TeamRepositoryCustom, JpaSpecificationExecutor<TeamEntity> {
}
