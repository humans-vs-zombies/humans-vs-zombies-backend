package com.example.humansvszombiesbackend.controller;

import com.example.humansvszombiesbackend.model.dbo.Game;
import com.example.humansvszombiesbackend.model.dto.Response;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.NestedServletException;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureTestDatabase
class GameControllerTest {

    private final String HOST = "http://localhost:";
    private final String PATH = "/api/v1/game";

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
    @WithMockUser(roles = {"user"})
    void findAllGames_statusOk_shouldPass() throws Exception {
        mockMvc.perform(
                get(getRootUrl() + "?limit=1&offset=0")
        ).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"user"})
    @Transactional
    void saveGame_whenUser_ThrowsException() {
        Game game = Game.builder()
                .name("game_name")
                .description("game_description")
                .dateFrom(new Date())
                .dateTo(new Date())
                .build();

        assertThrows(NestedServletException.class, () -> mockMvc.perform(
                post(getRootUrl())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(game))
        ));
    }

    @Test
    @WithMockUser(roles = {"admin"})
    @Transactional
    void saveGame_statusOk_shouldPass() throws Exception {
        Game game = Game.builder()
                .name("game_name")
                .description("game_description")
                .dateFrom(new Date())
                .dateTo(new Date())
                .build();

        String gameJson = objectMapper.writeValueAsString(game);

        mockMvc.perform(
                post(getRootUrl())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gameJson)
        ).andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = {"admin"})
    @Transactional
    void saveGame_whenInvalidGameObject_returnsBadRequest() throws Exception {
        Game game = null;

        String gameJson = objectMapper.writeValueAsString(game);

        mockMvc.perform(
                post(getRootUrl())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gameJson)
        ).andExpect(status().isBadRequest());
    }


    @Test
    @WithMockUser(roles = {"admin"})
    @Transactional
    void findAllGamesForAdmin_afterPostingGame_ShouldContain() throws Exception {
        Game game = Game.builder()
                .name("game_name")
                .description("game_description")
                .dateFrom(new Date())
                .dateTo(new Date())
                .build();

        String saveResponse = mockMvc.perform(
                post(getRootUrl())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(game))
        ).andReturn().getResponse().getContentAsString();

        JsonNode responseJson = objectMapper.readTree(saveResponse);
        Game saveResponseGame = objectMapper.readValue(responseJson.get("payload").toString(), Game.class);

        mockMvc.perform(
                get(getRootUrl() + "/for-admin?limit=1&offset=0")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(content().json(objectMapper.writeValueAsString(new Response<>(List.of(saveResponseGame)))));
    }

    @Test
    @WithMockUser(roles = {"admin"})
    @Transactional
    void findGame_afterPostingGame_findGameShouldContain() throws Exception {
        Game game = Game.builder()
                .name("game_name")
                .description("game_description")
                .dateFrom(new Date())
                .dateTo(new Date())
                .build();

        String saveResponse = mockMvc.perform(
                post(getRootUrl())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(game))
        ).andReturn().getResponse().getContentAsString();

        JsonNode responseJson = objectMapper.readTree(saveResponse);
        Game saveResponseGame = objectMapper.readValue(responseJson.get("payload").toString(), Game.class);

        mockMvc.perform(
                get(getRootUrl() + "/" + saveResponseGame.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(content().json(saveResponse));
    }

}