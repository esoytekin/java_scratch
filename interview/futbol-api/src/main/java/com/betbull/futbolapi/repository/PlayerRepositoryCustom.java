package com.betbull.futbolapi.repository;

import com.betbull.futbolapi.model.PlayerEntity;

import java.util.List;

public interface PlayerRepositoryCustom {
    List<PlayerEntity> findByNameAndSurname(String name, String surname);
}
