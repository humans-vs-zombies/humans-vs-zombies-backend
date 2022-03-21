package com.example.humansvszombiesbackend.controller;

import com.example.humansvszombiesbackend.model.dbo.Player;
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

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RequiredArgsConstructor
@SecurityRequirement(name = "openId")
@RequestMapping("api/v1/game/{gameId}/player")
@RestController
public class PlayerController {

    private final PlayerRepository players;

    @GetMapping
    @RolesAllowed({"user", "admin"})
    public ResponseEntity<Response<List<?>>> findAllPlayers(
            @PathVariable Integer gameId,
            KeycloakAuthenticationToken token) {
        List<Player> playerList = this.players.findPlayersByCurrentGameId(gameId);
        if (token != null && token.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_admin"))) {
            return ResponseEntity.ok(new Response<>(playerList));
        }
        return ResponseEntity.ok(new Response<>(playerList.stream().map(PlayerDTO::from).toList()));
    }

    @GetMapping("{id}")
    @RolesAllowed({"user", "admin"})
    public ResponseEntity<Response<Object>> findPlayer(
            @PathVariable Integer gameId,
            @PathVariable Integer id,
            KeycloakAuthenticationToken token
    ) {
        return players.findPlayerByCurrentGameIdAndId(gameId, id)
                .map(player -> // Player found
                        ResponseEntity.ok(new Response<>(
                                token != null && token.getAuthorities().stream()
                                        .anyMatch(authority -> authority.getAuthority()
                                                .equals("ROLE_admin"))
                                        ? player // Admin, return complete object
                                        : PlayerDTO.from(player) // not Admin, return DTO
                        ))
                ).orElse( // Player not found
                        ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(new Response<>("Player not found"))
                );
    }

}
