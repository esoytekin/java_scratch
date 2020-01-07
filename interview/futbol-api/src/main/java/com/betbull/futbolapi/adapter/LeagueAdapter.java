package com.betbull.futbolapi.adapter;

import com.betbull.futbolapi.model.LeagueEntity;
import com.betbull.futbolapi.repository.LeagueRepository;
import com.betbull.futbolapi.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.swagger.futbolapi.rest.v1.model.LeagueRequest;
import org.swagger.futbolapi.rest.v1.model.LeagueResponse;

@Component
@RequiredArgsConstructor
public class LeagueAdapter {

    private final ModelMapper modelMapper;
    private final LeagueRepository leagueRepository;

    public LeagueResponse createLeagueEntity(LeagueRequest leagueEntity) {

        final LeagueEntity entity = modelMapper.map(leagueEntity, LeagueEntity.class);
        final LeagueEntity savedEntity = leagueRepository.save(entity);
        return modelMapper.map(savedEntity, LeagueResponse.class);

    }

}
