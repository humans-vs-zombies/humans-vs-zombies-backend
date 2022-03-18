package com.example.humansvszombiesbackend.controller;

import com.example.humansvszombiesbackend.model.dbo.Game;
import com.example.humansvszombiesbackend.model.dbo.Player;
import com.example.humansvszombiesbackend.model.dto.Response;
import com.example.humansvszombiesbackend.repository.GameRepository;
import com.example.humansvszombiesbackend.service.GamePlayerService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/game")
public class GameController {

    private final GameRepository games;
    private final GamePlayerService gamePlayers;

    @GetMapping
    @PermitAll
    public ResponseEntity<Response<List<Game>>> findAllGames() {
        return ResponseEntity.ok(new Response<>(games.findAll()));
    }

    @SneakyThrows
    @PostMapping
    @RolesAllowed("admin")
    public ResponseEntity<Response<Game>> saveGame(
            @RequestBody(required = false) Game game
    ) {

        if (game == null) {
            return ResponseEntity.badRequest()
                    .body(new Response<>("Invalid game object supplied"));
        }

        Game savedGame = games.save(game);
        URI uri = new URI("/api/v1/game/" + savedGame.getId());
        return ResponseEntity.created(uri).body(new Response<>(savedGame));
    }

    @PostMapping("join/{gameId}")
    @RolesAllowed({"admin", "user"})
    public ResponseEntity<Response<Player>> joinGame(
            @PathVariable Integer gameId,
            KeycloakAuthenticationToken keycloakAuthToken
    ) {
        UUID userId = UUID.fromString(keycloakAuthToken.getAccount().getKeycloakSecurityContext().getToken().getId());
        Response<Player> response = gamePlayers.createPlayer(gameId, userId);
        if (response.isSuccess())
            return ResponseEntity.ok(response);
        return ResponseEntity.badRequest().body(response);
    }
}
