package com.example.humansvszombiesbackend;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
// TODO: Configure CORS
@CrossOrigin("*")
@RestController
@SecurityScheme(name = "humans-vs-zombies", openIdConnectUrl = "https://humans-vs-zombies-keycloak.herokuapp.com/auth/realms/humans-vs-zombies/.well-known/openid-configuration", type = SecuritySchemeType.OPENIDCONNECT, in = SecuritySchemeIn.HEADER)
public class HumansVsZombiesBackendApplication {

    @GetMapping("test")
    public String hello()
    {
        return "Hello world!";
    }

    public static void main(String[] args) {
        SpringApplication.run(HumansVsZombiesBackendApplication.class, args);
    }

}
