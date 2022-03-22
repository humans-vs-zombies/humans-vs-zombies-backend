package com.example.humansvszombiesbackend.controller;

import com.example.humansvszombiesbackend.model.dbo.Kill;
import com.example.humansvszombiesbackend.model.dbo.Player;
import com.example.humansvszombiesbackend.model.dto.KillRequestDTO;
import com.example.humansvszombiesbackend.model.dto.Response;
import com.example.humansvszombiesbackend.repository.KillRepository;
import com.example.humansvszombiesbackend.repository.PlayerRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RequiredArgsConstructor
@SecurityRequirement(name = "openId")
@RestController
@RequestMapping("api/v1/game/{gameId}/kill")
public class KillController {

    private final PlayerRepository players;
    private final KillRepository kills;

    @GetMapping
    public ResponseEntity<Response<List<Kill>>> findKills(@PathVariable Integer gameId) {
        return ResponseEntity.ok(new Response<>(kills.findAll()));
    }

    @GetMapping("{killId}")
    public ResponseEntity<Response<Kill>> findKill(
            @PathVariable Integer gameId,
            @PathVariable Integer killId
    ) {
        return kills.findByGameAndId(gameId, killId)
                .map( // Kill found
                        killFound -> ResponseEntity.ok(new Response<>(killFound))
                ).orElse( // Not found
                        ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(new Response<>("Kill not found")));
    }

    @RolesAllowed({"admin"})
    @PostMapping
    public ResponseEntity<Response<Kill>> createKill(
            @PathVariable Integer gameId,
            @RequestBody KillRequestDTO killRequest
    ) {
        Optional<Player> killer = players.findPlayerByCurrentGameIdAndId(gameId, killRequest.getKillerId());

        if (killer.isEmpty()) // Not found within game
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Response<>("Killer was not found"));

        else if (killer.get().isHuman()) // Is human (can't kill)
            return ResponseEntity.badRequest()
                    .body(new Response<>("Killer is human"));

        return players.findPlayerByCurrentGameIdAndBiteCode(
                        gameId,
                        killRequest.getBiteCode().toUpperCase(Locale.ROOT)
                ).map(victim -> { // Found victim, creating kill object

                    if (!victim.isHuman()) // Victim is zombie (can't be killed)
                        return ResponseEntity.badRequest()
                                .body(new Response<Kill>("Victim is not human"));

                    Kill kill = Kill.builder()
                            .game(victim.getCurrentGame())
                            .victim(victim)
                            .killer(killer.get())
                            .build();

                    // Set victim to zombie
                    victim.setHuman(false);
                    players.save(victim);

                    return ResponseEntity.ok(new Response<>(kills.save(kill)));
                })
                .orElse( // Did not find victim
                        ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(new Response<>("Could not find victim within game"))
                );

    }

}
