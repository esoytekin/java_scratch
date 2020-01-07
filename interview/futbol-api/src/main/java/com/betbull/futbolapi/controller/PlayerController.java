package com.betbull.futbolapi.controller;

import com.betbull.futbolapi.adapter.PlayerAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.swagger.futbolapi.rest.v1.PlayerApi;
import org.swagger.futbolapi.rest.v1.model.LeagueResponse;
import org.swagger.futbolapi.rest.v1.model.PlayerRequest;
import org.swagger.futbolapi.rest.v1.model.PlayerResponse;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Controller
@RequiredArgsConstructor
public class PlayerController implements PlayerApi{

    private final PlayerAdapter playerAdapter;

    @Override
    public CompletableFuture<ResponseEntity<PlayerResponse>> createPlayer(@Valid PlayerRequest player) {

        PlayerResponse response = playerAdapter.createPlayer(player);
        return CompletableFuture.completedFuture(ResponseEntity.ok(response));

    }

    @Override
    public CompletableFuture<ResponseEntity<Void>> updatePlayer(@Valid PlayerResponse player) {
        playerAdapter.updatePlayer(player);
        return CompletableFuture.completedFuture(new ResponseEntity<>(HttpStatus.OK));
    }

    @Override
    public CompletableFuture<ResponseEntity<Void>> deletePlayerById(Long playerId) {
        playerAdapter.deletePlayerById(playerId);
        return CompletableFuture.completedFuture(new ResponseEntity<>(HttpStatus.OK));
    }

    @Override
    public CompletableFuture<ResponseEntity<List<PlayerResponse>>> getPlayerList(@Valid String name, @Valid String surname) {

        List<PlayerResponse> responseList = playerAdapter.getPlayerList(name, surname);
        return CompletableFuture.completedFuture(ResponseEntity.ok(responseList));

    }

    @Override
    public CompletableFuture<ResponseEntity<List<LeagueResponse>>> getContractsByPlayer(Long playerId) {

        List<LeagueResponse> leaguesByPlayer = playerAdapter.getLeaguesByPlayer(playerId);
        return CompletableFuture.completedFuture(ResponseEntity.ok(leaguesByPlayer));

    }

}
