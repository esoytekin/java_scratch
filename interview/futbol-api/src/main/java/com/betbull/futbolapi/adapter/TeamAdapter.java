package com.betbull.futbolapi.adapter;

import com.betbull.futbolapi.model.Currency;
import com.betbull.futbolapi.model.LeagueEntity;
import com.betbull.futbolapi.model.TeamEntity;
import com.betbull.futbolapi.repository.LeagueRepository;
import com.betbull.futbolapi.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.swagger.futbolapi.rest.v1.model.LeagueResponse;
import org.swagger.futbolapi.rest.v1.model.TeamRequest;
import org.swagger.futbolapi.rest.v1.model.TeamResponse;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TeamAdapter {

    private final ModelMapper modelMapper;

    private final TeamRepository teamRepository;
    private final LeagueRepository leagueRepository;

    public TeamResponse createTeam(TeamRequest team) {
        TeamEntity teamEntity = modelMapper.map(team, TeamEntity.class);
        final TeamEntity savedEntity = teamRepository.save(teamEntity);
        return modelMapper.map(savedEntity, TeamResponse.class);
    }

    public void updateTeam(TeamResponse teamResponse) {
        Long teamId = teamResponse.getId();
        Optional<TeamEntity> teamInDbOptional = teamRepository.findById(teamId);

        if (!teamInDbOptional.isPresent()) {
            throw new IllegalArgumentException("team with id not found!");
        }

        TeamEntity teamInbDb = teamInDbOptional.get();

        teamInbDb.setName(teamResponse.getName());
        teamInbDb.setCurrency(Currency.valueOf(teamResponse.getCurrency().name()));

        teamRepository.save(teamInbDb);
    }

    public void deleteTeamById(Long teamId) {
        teamRepository.deleteById(teamId);
    }

    public List<TeamResponse> getTeamList(String name) {

        final List<TeamEntity> teamList = teamRepository.findByName(name);
        return teamList.stream()
                .map(entity -> modelMapper.map(entity, TeamResponse.class))
                .collect(Collectors.toList());

    }

    public List<LeagueResponse> getLeaguesByTeamAndYear(Long teamId, int year) {

        List<LeagueEntity> leaguesByTeamAndYear = leagueRepository.findLeaguesByTeamAndYear(teamId, year);

        return leaguesByTeamAndYear.stream()
                .map(leagueEntity -> modelMapper.map(leagueEntity, LeagueResponse.class))
                .collect(Collectors.toList());
    }
}
