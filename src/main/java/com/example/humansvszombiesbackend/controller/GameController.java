package com.example.humansvszombiesbackend.controller;

import com.example.humansvszombiesbackend.enums.GameState;
import com.example.humansvszombiesbackend.model.dbo.Game;
import com.example.humansvszombiesbackend.model.dto.GameCreateDTO;
import com.example.humansvszombiesbackend.model.dto.Response;
import com.example.humansvszombiesbackend.repository.GameRepository;
import com.example.humansvszombiesbackend.service.GameStateService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.websocket.server.PathParam;
import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@SecurityRequirement(name = "openId")
@RequestMapping("/api/v1/game")
@CrossOrigin(
        origins = {
                "http://localhost:3000",
                "https://humans-vs-zombies-frontend.herokuapp.com"
        },
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.OPTIONS}
)
public class GameController {

    private final GameRepository games;
    private final GameStateService gameStates;

    @GetMapping
    @PermitAll
    public ResponseEntity<Response<List<Game>>> findAllGames(
            @RequestParam Integer limit,
            @RequestParam Integer offset,
            @RequestParam(required = false, value = "state") String selectedFilter
    ) {
        Pageable pageable = PageRequest.of(offset,limit);

        if (selectedFilter == null) {
            // Return all games (except configuration state)
            return ResponseEntity.ok(new Response<>(games.findAllByStateNotOrderByStateAscDateFromAscNameAsc(GameState.CONFIGURATION, pageable)));
        }
        else {
            // Return games filtered by state
            GameState stateToFilter = switch (selectedFilter) {
                case "IN_PROGRESS" -> GameState.IN_PROGRESS;
                case "COMPLETE" -> GameState.COMPLETE;
                default -> GameState.REGISTRATION;
            };
            return ResponseEntity.ok(new Response<>(games.findAllByStateEqualsOrderByDateFromAscNameAsc(stateToFilter, pageable)));
        }
    }

    @GetMapping("/for-admin")
    @RolesAllowed("admin")
    public ResponseEntity<Response<List<Game>>> findAllGamesForAdmin() {
        return ResponseEntity.ok(new Response<>(games.findAllByOrderByStateAscDateFromAscNameAsc()));
    }

    @GetMapping("/configuration")
    @RolesAllowed("admin")
    public ResponseEntity<Response<List<Game>>> findAllGamesWithStateConfiguration(
            @RequestParam Integer limit,
            @RequestParam Integer offset
    ) {
        Pageable pageable = PageRequest.of(offset,limit);
        return ResponseEntity.ok(new Response<>(games.findAllByStateEqualsOrderByDateFromAscNameAsc(GameState.CONFIGURATION, pageable)));
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
            @RequestBody(required = false) GameCreateDTO game
    ) {

        if (game == null) {
            return ResponseEntity.badRequest()
                    .body(new Response<>("Invalid game object supplied"));
        }

        Game savedGame = games.save(Game.builder()
                .name(game.getName())
                        .participants(game.getParticipants())
                        .dateFrom(game.getDateFrom())
                        .dateTo(game.getDateTo())
                        .description(game.getDescription())
                .build());
        URI uri = new URI("/api/v1/game/" + savedGame.getId());
        return ResponseEntity.created(uri).body(new Response<>(savedGame));
    }

    @DeleteMapping("{id}")
    @RolesAllowed("admin")
    public ResponseEntity<Response<Boolean>> deleteGame(
            @PathVariable Integer id
    ) {
        if (!games.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Response<>("Game with the specified id was not found"));
        }

        games.deleteById(id);
        return ResponseEntity.accepted().body(new Response<>(true));
    }

    @PostMapping("{id}/next-state")
    @RolesAllowed({"admin"})
    public ResponseEntity<Response<GameState>> nextGameState(
            @PathVariable Integer id
    ) {
        return games.findById(id).map(
                game -> {
                    GameState nextState = gameStates.nextState(game.getState());

                    if (game.getState() == nextState)
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(new Response<GameState>("Game is in the final state"));

                    game.setState(nextState);

                    games.save(game);

                    return ResponseEntity.ok(new Response<>(game.getState()));
                }
        ).orElse(
                ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new Response<>("Game with the specified id was not found"))
        );
    }
}
