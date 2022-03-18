package com.example.humansvszombiesbackend.controller;

import com.example.humansvszombiesbackend.model.dto.Response;
import com.example.humansvszombiesbackend.model.dto.UserDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.IDToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.UUID;

@RestController
@SecurityRequirement(name = "openId")
@RequestMapping("api/v1/user")
public class UserController {

    @GetMapping
    public ResponseEntity<Response<UserDTO>> getUser(KeycloakAuthenticationToken keycloakToken) {

        if (keycloakToken == null || !keycloakToken.isAuthenticated())
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new Response<>("User is not authenticated"));

        IDToken identity = keycloakToken.getAccount().getKeycloakSecurityContext().getToken();

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
        if (keycloakToken == null || !keycloakToken.isAuthenticated())
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new Response<>("User is not authenticated"));

        return ResponseEntity.ok(new Response<>(
                keycloakToken.getAccount().getKeycloakSecurityContext().getTokenString(), true
        ));
    }

    // Test endpoint (temporary)
    @GetMapping("/isAdmin")
    @RolesAllowed("admin")
    public ResponseEntity<Response<String>> adminEndpoint()
    {
        return ResponseEntity.ok(new Response<>("User is 'admin'", true));
    }

    // Test endpoint (temporary)
    @GetMapping("/isUser")
    @RolesAllowed("user")
    public ResponseEntity<Response<String>> userEndpoint()
    {
        return ResponseEntity.ok(new Response<>("User is 'user'", true));
    }
}
