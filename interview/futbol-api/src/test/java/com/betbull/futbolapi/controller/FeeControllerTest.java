package com.betbull.futbolapi.controller;

import com.betbull.futbolapi.TestConfig;
import com.betbull.futbolapi.adapter.FeeAdapter;
import com.betbull.futbolapi.model.Currency;
import com.betbull.futbolapi.model.LeagueEntity;
import com.betbull.futbolapi.model.PlayerEntity;
import com.betbull.futbolapi.model.TeamEntity;
import com.betbull.futbolapi.repository.LeagueRepository;
import com.betbull.futbolapi.repository.PlayerRepository;
import com.betbull.futbolapi.repository.TeamRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Import(TestConfig.class)
@WebMvcTest({FeeController.class, FeeAdapter.class})
class FeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlayerRepository playerRepository;

    @MockBean
    private TeamRepository teamRepository;

    @MockBean
    private LeagueRepository leagueRepository;

    @Test
    @DisplayName("test get fee response")
    void testGetFeeResponse() throws Exception {

        PlayerEntity mockPlayer = new PlayerEntity();
        mockPlayer.setId(1l);
        mockPlayer.setName("player1-name");
        mockPlayer.setSurname("player1-surname");
        mockPlayer.setBirthDate(LocalDate.of(2000, 1, 12));
        Mockito.when(playerRepository.findById(1l)).thenReturn(Optional.of(mockPlayer));

        TeamEntity mockTeam = new TeamEntity();
        mockTeam.setId(1l);
        mockTeam.setName("team1");
        mockTeam.setCurrency(Currency.EUR);

        Mockito.when(teamRepository.findById(1l)).thenReturn(Optional.of(mockTeam));


        List<LeagueEntity> leagueEntities = new ArrayList<>();
        LeagueEntity mockLeagueEntity = new LeagueEntity();
        mockLeagueEntity.setId(1);
        mockLeagueEntity.setPlayer(mockPlayer);
        mockLeagueEntity.setTeam(mockTeam);
        mockLeagueEntity.setStartDate(LocalDate.of(2012, 2, 1));
        mockLeagueEntity.setEndDate(LocalDate.of(2013, 9, 1));

        leagueEntities.add(mockLeagueEntity);

        Mockito.when(leagueRepository.findLeaguesByPlayer(mockPlayer)).thenReturn(leagueEntities);


        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/fee/1/1"))
                .andExpect(MockMvcResultMatchers.request().asyncStarted())
                .andReturn();

        double expectedTransferFee = 100000;
        double expectedCommission = expectedTransferFee * 0.1;
        double expectedContractFee = expectedTransferFee + expectedCommission;
        String formatter = "%.2f %s";

        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.transferFee" ).value(String.format(formatter,expectedTransferFee,Currency.EUR)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.teamCommission").value(String.format(formatter, expectedCommission, Currency.EUR)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.contractFee").value(String.format(formatter, expectedContractFee, Currency.EUR)));

    }
}
