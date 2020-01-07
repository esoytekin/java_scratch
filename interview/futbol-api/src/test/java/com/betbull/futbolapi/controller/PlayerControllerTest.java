package com.betbull.futbolapi.controller;

import com.betbull.futbolapi.TestConfig;
import com.betbull.futbolapi.Util;
import com.betbull.futbolapi.adapter.PlayerAdapter;
import com.betbull.futbolapi.model.LeagueEntity;
import com.betbull.futbolapi.model.PlayerEntity;
import com.betbull.futbolapi.repository.LeagueRepository;
import com.betbull.futbolapi.repository.PlayerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Import(TestConfig.class)
@WebMvcTest({PlayerController.class, PlayerAdapter.class})
class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlayerRepository playerRepository;

    @MockBean
    private LeagueRepository leagueRepository;

    @Test
    void testCreatePlayer() throws Exception {

        String name = "player1-name";
        String surname = "player1-surname";
        String birthDate = "2000-02-15";


        String content = String.format("{\"name\":\"%s\", \"surname\":\"%s\", \"birthdate\":\"%s\"}", name, surname, birthDate );


        ArgumentCaptor<PlayerEntity> argumentCaptor = ArgumentCaptor.forClass(PlayerEntity.class);
        Mockito.when(playerRepository.save(argumentCaptor.capture())).thenAnswer((Answer) invocationOnMock -> argumentCaptor.getValue());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/player").contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(MockMvcResultMatchers.request().asyncStarted())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname").value(surname))
                .andExpect(MockMvcResultMatchers.jsonPath("$.birthdate").value(birthDate));

    }

    @Test
    void testGetContractsByPlayer() throws Exception {

        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setName("player1-name");
        playerEntity.setSurname("player1-surname");
        playerEntity.setBirthDate(LocalDate.parse("1996-07-27"));
        playerEntity.setId(1l);

        Mockito.when(playerRepository.findById(1l)).thenReturn(Optional.of(playerEntity));
        List<LeagueEntity> leagueEntities = new ArrayList<>();
        leagueEntities.add(Util.createLeagueEntity("2015-01-02", "2015-02-02"));
        leagueEntities.add(Util.createLeagueEntity("2015-04-01", "2015-07-02"));
        leagueEntities.add(Util.createLeagueEntity("2015-07-01", "2015-12-02"));

        Mockito.when(leagueRepository.findLeaguesByPlayer(playerEntity)).thenReturn(leagueEntities);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/player/1"))
                .andExpect(MockMvcResultMatchers.request().asyncStarted())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].startDate").value("2015-01-02"))
                ;

    }
}
