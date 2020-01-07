package com.betbull.futbolapi.adapter;

import com.betbull.futbolapi.model.LeagueEntity;
import com.betbull.futbolapi.model.PlayerEntity;
import com.betbull.futbolapi.repository.LeagueRepository;
import com.betbull.futbolapi.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.swagger.futbolapi.rest.v1.model.LeagueResponse;
import org.swagger.futbolapi.rest.v1.model.PlayerRequest;
import org.swagger.futbolapi.rest.v1.model.PlayerResponse;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PlayerAdapter {

    private final ModelMapper modelMapper;

    private final PlayerRepository playerRepository;

    private final LeagueRepository leagueRepository;

    public PlayerResponse createPlayer(PlayerRequest player) {
        PlayerEntity playerEntity = modelMapper.map(player, PlayerEntity.class);
        playerEntity = playerRepository.save(playerEntity);
        return modelMapper.map(playerEntity, PlayerResponse.class);
    }

    public void updatePlayer(PlayerResponse player) {

        Optional<PlayerEntity> playerInDbOptional = playerRepository.findById(player.getId());
        if (!playerInDbOptional.isPresent()) {
            throw new IllegalArgumentException("player not found!");
        }

        PlayerEntity playerInDB = playerInDbOptional.get();
        playerInDB.setName(player.getName());
        playerInDB.setSurname(player.getSurname());
        playerInDB.setBirthDate(player.getBirthdate());

        playerRepository.save(playerInDB);
    }

    public void deletePlayerById(Long playerId) {
        playerRepository.deleteById(playerId);
    }

    public List<PlayerResponse> getPlayerList(String name, String surname) {

        List<PlayerEntity> players = playerRepository.findByNameAndSurname(name, surname);
        return players.stream().map(playerEntity -> modelMapper.map(playerEntity, PlayerResponse.class)).collect(Collectors.toList());
    }

    public List<LeagueResponse> getLeaguesByPlayer(Long playerId) {

        Optional<PlayerEntity> byId = playerRepository.findById(playerId);

        if (!byId.isPresent()) {
            throw new IllegalArgumentException("player does not exist");
        }

        List<LeagueEntity> leaguesByPlayer = leagueRepository.findLeaguesByPlayer(byId.get());

        return leaguesByPlayer.stream()
                .map(leagueEntity -> modelMapper.map(leagueEntity, LeagueResponse.class))
                .collect(Collectors.toList());

    }
}
