package com.betbull.futbolapi.controller;

import com.betbull.futbolapi.TestConfig;
import com.betbull.futbolapi.Util;
import com.betbull.futbolapi.adapter.TeamAdapter;
import com.betbull.futbolapi.model.LeagueEntity;
import com.betbull.futbolapi.model.TeamEntity;
import com.betbull.futbolapi.repository.LeagueRepository;
import com.betbull.futbolapi.repository.TeamRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.swagger.futbolapi.rest.v1.model.TeamRequest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Import(TestConfig.class)
@WebMvcTest({TeamController.class, TeamAdapter.class})
class TeamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LeagueRepository leagueRepository;

    @MockBean
    private TeamRepository teamRepository;

    @Test
    void testCreateTeam() throws Exception {

        TeamRequest teamRequest = new TeamRequest();
        teamRequest.setName("team1");
        teamRequest.setCurrency(TeamRequest.CurrencyEnum.TL);
        String content = new ObjectMapper().writeValueAsString(teamRequest);

        ArgumentCaptor<TeamEntity> argumentCaptor = ArgumentCaptor.forClass(TeamEntity.class);

        Mockito.when(teamRepository.save(argumentCaptor.capture())).thenAnswer((Answer) invocationOnMock -> argumentCaptor.getValue());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/team").contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(MockMvcResultMatchers.request().asyncStarted())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("team1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currency").value("TL"));

    }

    @Test
    void testGetContractsByTeamAndYear() throws Exception {

        List<LeagueEntity> leagueEntities = new ArrayList<>();
        String startDate = "2015-01-01";
        String endDate = "2015-03-01";
        LeagueEntity leagueEntity = Util.createLeagueEntity(startDate, endDate);
        leagueEntities.add(leagueEntity);
        Mockito.when(leagueRepository.findLeaguesByTeamAndYear(1l, 2015)).thenReturn(leagueEntities);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/team/1/2015"))
                .andExpect(MockMvcResultMatchers.request().asyncStarted())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].startDate").value(startDate))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].endDate").value(endDate)) ;
    }


}
