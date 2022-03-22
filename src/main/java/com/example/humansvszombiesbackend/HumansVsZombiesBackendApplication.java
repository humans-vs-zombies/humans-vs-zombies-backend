package com.example.humansvszombiesbackend;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableWebSecurity
@SpringBootApplication
// TODO: Configure CORS
@RestController
@SecurityScheme(name = "openId", openIdConnectUrl = "https://humans-vs-zombies-keycloak.herokuapp.com/auth/realms/humans-vs-zombies/.well-known/openid-configuration", type = SecuritySchemeType.OPENIDCONNECT, in = SecuritySchemeIn.HEADER)
@ConfigurationPropertiesScan
@EnableConfigurationProperties
public class HumansVsZombiesBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(HumansVsZombiesBackendApplication.class, args);
    }
}
