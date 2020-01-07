package com.betbull.futbolapi.repository;

import com.betbull.futbolapi.model.TeamEntity;

import java.util.List;

public interface TeamRepositoryCustom {
    List<TeamEntity> findByName(String name);
}
