package com.example.humansvszombiesbackend;

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
