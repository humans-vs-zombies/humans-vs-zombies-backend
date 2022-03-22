package com.example.humansvszombiesbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureTestDatabase
class PlayerControllerTest {

    private final String HOST = "http://localhost:";
    private final String PATH = "/api/v1/game/%d/player";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String getRootUrl(Integer gameId) {
        return HOST + String.format(PATH, gameId);
    }

    @Test
    void contextLoads() {
        assertNotNull(objectMapper);
        assertNotNull(mockMvc);
    }

    @Test
    @WithMockUser(roles = "user")
    void findAllPlayers_whenUser_returnsOk() {

    }

    @Test
    @WithMockUser(roles = "admin")
    void findAllPlayers_whenAdmin_returnsOk() {

    }

    @Test
    void findAllPlayers_whenAnonymous_returnsUnauthorized() {

    }

}