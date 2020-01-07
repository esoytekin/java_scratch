package com.betbull.futbolapi.controller;

import com.betbull.futbolapi.adapter.FeeAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.swagger.futbolapi.rest.v1.FeeApi;
import org.swagger.futbolapi.rest.v1.model.FeeResponse;

import java.util.concurrent.CompletableFuture;

@Controller
@RequiredArgsConstructor
public class FeeController implements FeeApi{

    private final FeeAdapter feeAdapter;

    @Override
    public CompletableFuture<ResponseEntity<FeeResponse>> getFeeResponse(Long playerId, Long teamId) {

        FeeResponse feeResponseByPlayer = feeAdapter.getFeeResponseByPlayer(playerId, teamId);
        return CompletableFuture.completedFuture(ResponseEntity.ok(feeResponseByPlayer));

    }
}
