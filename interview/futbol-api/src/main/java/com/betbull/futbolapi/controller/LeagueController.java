package com.betbull.futbolapi.controller;

import com.betbull.futbolapi.adapter.LeagueAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.swagger.futbolapi.rest.v1.LeagueApi;
import org.swagger.futbolapi.rest.v1.model.LeagueRequest;
import org.swagger.futbolapi.rest.v1.model.LeagueResponse;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

@Controller
@RequiredArgsConstructor
public class LeagueController implements LeagueApi {

    private final LeagueAdapter adapter;

    @Override
    public CompletableFuture<ResponseEntity<LeagueResponse>> createLeagueEntity(@Valid LeagueRequest leagueEntity) {
        final LeagueResponse response = adapter.createLeagueEntity(leagueEntity);
        return CompletableFuture.completedFuture(ResponseEntity.ok(response));
    }

    @Override
    public CompletableFuture<ResponseEntity<LeagueResponse>> endContract(@Valid LeagueRequest leagueEntity) {
        return CompletableFuture.completedFuture(new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED));
    }
}
