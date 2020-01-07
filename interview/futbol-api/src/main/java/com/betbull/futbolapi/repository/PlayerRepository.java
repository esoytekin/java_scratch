package com.betbull.futbolapi.repository;

import com.betbull.futbolapi.model.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PlayerRepository extends JpaRepository<PlayerEntity, Long>, PlayerRepositoryCustom, JpaSpecificationExecutor<PlayerEntity> {
}
