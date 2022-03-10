package com.example.humansvszombiesbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/game")
public class GameController {

    @GetMapping
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Heisann!");
    }
}
