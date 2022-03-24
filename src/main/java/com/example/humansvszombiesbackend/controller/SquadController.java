package com.example.humansvszombiesbackend.controller;

import com.example.humansvszombiesbackend.model.dbo.*;
import com.example.humansvszombiesbackend.model.dto.Response;
import com.example.humansvszombiesbackend.model.dto.SquadCreateDTO;
import com.example.humansvszombiesbackend.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/game/{gameId}/squad")
public class SquadController {

    private final GameRepository games;
    private final SquadRepository squads;
    private final PlayerRepository players;

    @GetMapping
    public ResponseEntity<Response<List<Squad>>> findAllSquads(
            @PathVariable Integer gameId
    ) {
        return ResponseEntity.ok(new Response<>(squads.findAllByGameId(gameId)));
    }

    @GetMapping("{squadId}")
    public ResponseEntity<Response<Squad>> findSquad(
            @PathVariable Integer gameId,
            @PathVariable Integer squadId
    ) {
        return squads.findByGameIdAndId(gameId, squadId).map(
                squad -> ResponseEntity.ok(new Response<>(squad))
        ).orElse(ResponseEntity.ok(new Response<>("Squad was not found")));
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<Response<Squad>> saveSquad(
            @PathVariable Integer gameId,
            @RequestBody SquadCreateDTO squadCreateDTO
    ) {

        Optional<Game> game = games.findById(gameId);
        Optional<Player> player = players.findById(squadCreateDTO.getPlayerId());


        if (game.isEmpty() || player.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>("Game or player was not found"));

        Squad createdSquad = squads.save(Squad.builder()
                .id(gameId)
                .game(game.get())
                .isHuman(player.get().isHuman())
                .build());
        return ResponseEntity
                .created(URI.create(String.format("api/v1/game/%d/squad/%d", gameId, createdSquad.getId())))
                .body(new Response<>(createdSquad));
    }

    @PostMapping("{squadId}/join")
    public ResponseEntity<Response<Squad>> joinSquad(
            @PathVariable Integer gameId,
            @PathVariable Integer squadId,
            @RequestBody Integer playerId
    ) {
        Optional<SquadMember> squadMember = squadMembers.findByGameIdAndSquadIdAndPlayerId(gameId, squadId, playerId);

        if (squadMember.isPresent())
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Response<>("Player is already in a squad"));


        return games.findById(gameId).map(
                foundGame -> squads.findByGameIdAndId(gameId, squadId).map(
                        foundSquad -> players.findPlayerByCurrentGameIdAndId(gameId, playerId).map(
                                foundPlayer -> {
                                    squadMembers.save(SquadMember.builder()
                                            .squad(foundSquad)
                                            .game(foundGame)
                                            .player(foundPlayer)
                                            .build());

                                    return ResponseEntity.ok(new Response<>(squads.getById(foundSquad.getId())));
                                }
                        ).orElse(ResponseEntity.ok(new Response<Squad>("Player was not found")))
                ).orElse(ResponseEntity.ok(new Response<Squad>("Squad was not found")))
        ).orElse(ResponseEntity.ok(new Response<Squad>("Game was not found")));
    }

    @PutMapping("{squadId}")
    @RolesAllowed({"admin"})
    public ResponseEntity<Response<Squad>> updateSquad(
            @PathVariable Integer gameId,
            @PathVariable Integer squadId,
            @RequestBody Squad squad
    ) {
        return null;
    }

    @DeleteMapping("{squadId}")
    @RolesAllowed({"admin"})
    public ResponseEntity<Response<Boolean>> deleteSquad(
            @PathVariable Integer gameId,
            @PathVariable Integer squadId
    ) {
        return null;
    }

    @GetMapping("{squadId}/chat")
    public ResponseEntity<Response<List<Chat>>> findAllChats(
            @PathVariable Integer gameId,
            @PathVariable Integer squadId
    ) {
        return null;
    }

    @PostMapping("{squadId}/chat")
    public ResponseEntity<Response<Chat>> sendChat(
            @PathVariable Integer gameId,
            @PathVariable Integer squadId,
            @RequestBody Chat chat
    ) {
        return null;
    }

    @GetMapping("{squadId}/check-in")
    public ResponseEntity<Response<List<SquadCheckIn>>> findAllSquadCheckInMarkers(
            @PathVariable Integer gameId,
            @PathVariable Integer squadId
    ) {
        return null;
    }

    @PostMapping("{squadId}/check-in")
    public ResponseEntity<Response<SquadCheckIn>> createSquadCheckInMarker(
            @PathVariable Integer gameId,
            @PathVariable Integer squadId,
            @RequestBody SquadCheckIn checkIn
    ) {
        return null;
    }

}
