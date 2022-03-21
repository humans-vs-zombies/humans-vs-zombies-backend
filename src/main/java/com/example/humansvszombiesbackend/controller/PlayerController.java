package com.example.humansvszombiesbackend.controller;

import com.example.humansvszombiesbackend.model.dto.PlayerDTO;
import com.example.humansvszombiesbackend.model.dto.Response;
import com.example.humansvszombiesbackend.repository.PlayerRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@SecurityRequirement(name = "openId")
@RequestMapping("api/v1/player")
@RestController
public class PlayerController {

    private final PlayerRepository players;

    @GetMapping
    public ResponseEntity<Response<List<?>>> findAllPlayers(KeycloakAuthenticationToken token)
    {
        if (token != null && token.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_admin")))
            return ResponseEntity.ok(new Response<>(players.findAll()));
        return ResponseEntity.ok(new Response<>(players.findAll().stream().map(PlayerDTO::from).toList()));
    }

    @GetMapping("{id}")
    public ResponseEntity<Response<Player>> findPlayer(@PathVariable Integer id)
    {
        return players.findById(id)
                .map(player -> // Player found
                        ResponseEntity.ok(new Response<>(player))
                ).orElse( // Player not found
                        ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(new Response<>("Player not found"))
                );
    }

}
