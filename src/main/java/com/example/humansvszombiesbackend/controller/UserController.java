package com.example.humansvszombiesbackend.controller;

import com.example.humansvszombiesbackend.model.dto.Response;
import com.example.humansvszombiesbackend.model.dto.UserDTO;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.IDToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    @GetMapping
    public ResponseEntity<Response<UserDTO>> getUser(KeycloakAuthenticationToken keycloakToken) {

        IDToken identity = keycloakToken.getAccount().getKeycloakSecurityContext().getIdToken();

        return ResponseEntity.ok(new Response<>(
                UserDTO.builder()
                        .id(UUID.fromString(identity.getId()))
                        .firstName(identity.getGivenName())
                        .lastName(identity.getFamilyName())
                        .build()
        ));
    }

    @GetMapping("token")
    public ResponseEntity<Response<String>> getToken(KeycloakAuthenticationToken keycloakToken) {
        return ResponseEntity.ok(new Response<>(
                keycloakToken.getAccount().getKeycloakSecurityContext().getTokenString(), true
        ));
    }

}
