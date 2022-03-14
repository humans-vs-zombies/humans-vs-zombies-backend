package com.example.humansvszombiesbackend.model.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Builder
@Data
public class UserDTO implements Serializable {

    private UUID id;

    private String firstName;

    private String lastName;

}
