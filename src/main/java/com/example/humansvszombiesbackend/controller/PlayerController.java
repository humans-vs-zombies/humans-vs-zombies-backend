package com.example.humansvszombiesbackend.controller;

import com.example.humansvszombiesbackend.model.dbo.Player;
import com.example.humansvszombiesbackend.model.dto.Response;
import com.example.humansvszombiesbackend.repository.PlayerRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@SecurityRequirement(name = "openId")
@RequestMapping("api/v1/player")
@RestController
public class PlayerController {

    private final PlayerRepository players;

    @GetMapping
    public ResponseEntity<Response<List<Player>>> findAllPlayers()
    {
        return ResponseEntity.ok(new Response<>(players.findAll()));
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
