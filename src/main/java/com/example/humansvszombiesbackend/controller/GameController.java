package com.example.humansvszombiesbackend.controller;

import com.example.humansvszombiesbackend.model.dbo.Game;
import com.example.humansvszombiesbackend.model.dbo.Player;
import com.example.humansvszombiesbackend.model.dto.Response;
import com.example.humansvszombiesbackend.repository.GameRepository;
import com.example.humansvszombiesbackend.service.GamePlayerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@SecurityRequirement(name = "openId")
@RequestMapping("/api/v1/game")
public class GameController {

    private final GameRepository games;
    private final GamePlayerService gamePlayers;

    @GetMapping
    @PermitAll
    public ResponseEntity<Response<List<Game>>> findAllGames() {
        return ResponseEntity.ok(new Response<>(games.findAll()));
    }

    @GetMapping("{id}")
    public ResponseEntity<Response<Game>> findGame(@PathVariable Integer id)
    {
        return games.findById(id)
                .map(game -> // Game found
                        ResponseEntity.ok(new Response<>(game))
                ).orElse( // Game not found
                        ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(new Response<>("Game not found"))
                );
    }

    @PutMapping("{id}")
    @RolesAllowed("admin")
    public ResponseEntity<Response<Game>> updateGame(
            @PathVariable Integer id,
            @RequestBody(required = false) Game game
    ){
        if (game == null) {
            return ResponseEntity.badRequest()
                    .body(new Response<>("Invalid game object supplied"));
        }
        else if (!games.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Response<>("Game with the specified id was not found"));
        }
        else {
            game.setId(id);

            Game patchedGame = games.save(game);

            return ResponseEntity.accepted()
                    .body(new Response<>(patchedGame));
        }
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

    @PostMapping("{gameId}/player")
    @RolesAllowed({"admin", "user"})
    public ResponseEntity<Response<Player>> joinGame(
            @PathVariable Integer gameId,
            KeycloakAuthenticationToken keycloakAuthToken
    ) {
        AccessToken token = keycloakAuthToken.getAccount().getKeycloakSecurityContext().getToken();
        Response<Player> response = gamePlayers.createPlayer(gameId, UUID.fromString(token.getId()), token.getName());
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
                        foundPlayer -> ResponseEntity.ok(gamePlayers.updatePlayer(playerId, updatedPlayer)))
                .orElse( // Player not found within foundGame
                        ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(new Response<>("Player with id " + playerId + " not found in game " + gameId)));
    }

}
