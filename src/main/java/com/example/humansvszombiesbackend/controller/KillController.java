package com.example.humansvszombiesbackend.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@SecurityRequirement(name = "openId")
@RestController
@RequestMapping("api/v1/game/{gameId}/kill")
public class KillController {

}
