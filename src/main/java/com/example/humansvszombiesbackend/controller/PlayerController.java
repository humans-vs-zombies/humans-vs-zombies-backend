package com.example.humansvszombiesbackend.controller;

import com.example.humansvszombiesbackend.model.dbo.Player;
import com.example.humansvszombiesbackend.model.dto.PlayerDTO;
import com.example.humansvszombiesbackend.model.dto.Response;
import com.example.humansvszombiesbackend.repository.PlayerRepository;
import com.example.humansvszombiesbackend.service.GamePlayerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@SecurityRequirement(name = "openId")
@RequestMapping("api/v1/game/{gameId}/player")
@RestController
public class PlayerController {

    private final PlayerRepository players;
    private final GamePlayerService gamePlayers;


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

    @PostMapping("{gameId}/player")
    @RolesAllowed({"admin", "user"})
    public ResponseEntity<Response<Player>> createPlayer(
            @PathVariable Integer gameId,
            KeycloakAuthenticationToken keycloakAuthToken
    ) {
        AccessToken token = keycloakAuthToken.getAccount().getKeycloakSecurityContext().getToken();
        Response<Player> response = gamePlayers.createPlayer(gameId, UUID.fromString(token.getSubject()), token.getName());
        if (response.isSuccess())
            return ResponseEntity.ok(response);
        return ResponseEntity.badRequest().body(response);
    }

    @PutMapping("{gameId}/player/{playerId}")
    @RolesAllowed({"admin"})
    public ResponseEntity<Response<Player>> updatePlayer(
            @PathVariable Integer gameId,
            @PathVariable Integer playerId,
            @RequestBody Player updatedPlayer
    ) {
        return gamePlayers.findPlayer(gameId, playerId)
                .map( // Player found
                        foundPlayer -> {
                            foundPlayer.setName(updatedPlayer.getName());
                            foundPlayer.setBiteCode(updatedPlayer.getBiteCode());
                            foundPlayer.setHuman(updatedPlayer.isHuman());
                            foundPlayer.setName(updatedPlayer.getName());
                            return ResponseEntity.ok(gamePlayers.updatePlayer(playerId, foundPlayer));
                        })
                .orElse( // Player not found within foundGame
                        ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(new Response<>("Player with id " + playerId + " not found in game " + gameId)));
    }

}
