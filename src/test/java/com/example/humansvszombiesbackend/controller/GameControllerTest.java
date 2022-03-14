package com.example.humansvszombiesbackend.controller;

import com.example.humansvszombiesbackend.enums.GameState;
import com.example.humansvszombiesbackend.model.dbo.Game;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc(addFilters = false)
class GameControllerTest {
    private String HOST = "http://localhost:";
    private String PATH = "/api/v1/game";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    private String getRootUrl() {
        return HOST + PATH;
    }

    @Test
    void contextLoads() {
        assertNotNull(objectMapper);
        assertNotNull(mockMvc);
    }

    @Test
    void findAllGames_statusOk_shouldPass() throws Exception {
        ResultActions resultActions = this.mockMvc.perform(get(getRootUrl()))
                .andExpect(status().isOk());
    }

    @Test
    void saveGame_statusOk_shouldPass() throws Exception {
        Game game = new Game(0, "game_name", GameState.CONFIGURATION, "game_description");
        String gameJson = objectMapper.writeValueAsString(game);

        ResultActions resultActions = this.mockMvc.perform(
                post(getRootUrl())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gameJson)
                )
                .andExpect(status().isCreated());
    }
}