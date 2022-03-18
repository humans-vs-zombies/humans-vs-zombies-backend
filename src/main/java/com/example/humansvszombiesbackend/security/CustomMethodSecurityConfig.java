package com.example.humansvszombiesbackend.security;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@EnableMethodSecurity(jsr250Enabled = true)
public class CustomMethodSecurityConfig {
}