package com.example.humansvszombiesbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
// TODO: Configure CORS
@CrossOrigin("*")
public class HumansVsZombiesBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(HumansVsZombiesBackendApplication.class, args);
    }

}
