package com.betbull.futbolapi.controller;

import com.betbull.futbolapi.adapter.TeamAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.swagger.futbolapi.rest.v1.TeamApi;
import org.swagger.futbolapi.rest.v1.model.LeagueResponse;
import org.swagger.futbolapi.rest.v1.model.TeamRequest;
import org.swagger.futbolapi.rest.v1.model.TeamResponse;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Controller
@RequiredArgsConstructor
public class TeamController implements TeamApi {

    private final TeamAdapter teamAdapter;

    @Override
    public CompletableFuture<ResponseEntity<TeamResponse>> createTeam(@Valid TeamRequest team) {

        final TeamResponse response = teamAdapter.createTeam(team);

        return CompletableFuture.completedFuture(ResponseEntity.ok(response));
    }

    @Override
    public CompletableFuture<ResponseEntity<Void>> updateTeam(@Valid TeamResponse team) {
        teamAdapter.updateTeam(team);
        return CompletableFuture.completedFuture(new ResponseEntity<>(HttpStatus.OK));
    }

    @Override
    public CompletableFuture<ResponseEntity<Void>> deleteTeamById(Long teamId) {
        teamAdapter.deleteTeamById(teamId);
        return CompletableFuture.completedFuture(new ResponseEntity<>(HttpStatus.OK));
    }

    @Override
    public CompletableFuture<ResponseEntity<List<TeamResponse>>> getTeamList(@Valid String name) {

        final List<TeamResponse> teamList = teamAdapter.getTeamList(name);

        return CompletableFuture.completedFuture(ResponseEntity.ok(teamList));
    }

    @Override
    public CompletableFuture<ResponseEntity<List<LeagueResponse>>> getContractsByTeamAndYear(Long teamId, Integer year) {
        List<LeagueResponse> leaguesByTeamAndYear = teamAdapter.getLeaguesByTeamAndYear(teamId, year);
        return CompletableFuture.completedFuture(ResponseEntity.ok(leaguesByTeamAndYear));
    }
}
