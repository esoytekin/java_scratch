package com.betbull.futbolapi.adapter;

import com.betbull.futbolapi.model.Currency;
import com.betbull.futbolapi.model.LeagueEntity;
import com.betbull.futbolapi.model.PlayerEntity;
import com.betbull.futbolapi.model.TeamEntity;
import com.betbull.futbolapi.repository.LeagueRepository;
import com.betbull.futbolapi.repository.PlayerRepository;
import com.betbull.futbolapi.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.swagger.futbolapi.rest.v1.model.FeeResponse;
import org.swagger.futbolapi.rest.v1.model.PlayerResponse;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FeeAdapter {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final LeagueRepository leagueRepository;
    private final ModelMapper modelMapper;

    public FeeResponse getFeeResponseByPlayer(Long playerId, Long teamId) {
        Optional<PlayerEntity> byIdPlayer = playerRepository.findById(playerId);
        Optional<TeamEntity> byIdTeam = teamRepository.findById(teamId);

        if (!byIdPlayer.isPresent()) {
            throw new IllegalArgumentException("player with id not exists!");
        }

        if (!byIdTeam.isPresent()) {
            throw new IllegalArgumentException("team with id not exists!");
        }

        PlayerEntity playerEntity = byIdPlayer.get();
        TeamEntity teamEntity = byIdTeam.get();

        FeeResponse feeResponse = new FeeResponse();
        feeResponse.setPlayer(modelMapper.map(playerEntity, PlayerResponse.class));

        populateFeeResponse(feeResponse, playerEntity, teamEntity);

        return feeResponse;

    }

    private void populateFeeResponse(FeeResponse feeResponse, PlayerEntity playerEntity, TeamEntity teamEntity) {
        Currency currency = teamEntity.getCurrency();
        int age = LocalDate.now().getYear() - playerEntity.getBirthDate().getYear();
        int experience = 0;
        List<LeagueEntity> leaguesByPlayer = leagueRepository.findLeaguesByPlayer(playerEntity);

        for (LeagueEntity leagueEntity : leaguesByPlayer) {
            LocalDate startDate = leagueEntity.getStartDate();
            LocalDate endDate = leagueEntity.getEndDate();

            if (endDate == null) {
                endDate = LocalDate.now();
            }


            experience += ChronoUnit.MONTHS.between(startDate, endDate);
        }

        String formatter = "%.2f %s";

        double transferFee = experience * 100000 / age;
        feeResponse.setTransferFee(String.format(formatter, transferFee, currency));
        double teamCommission = transferFee * 0.1;
        feeResponse.setTeamCommission(String.format(formatter, teamCommission, currency));

        double contractFee = transferFee + teamCommission;

        feeResponse.setContractFee(String.format(formatter, contractFee, currency));

    }

}
