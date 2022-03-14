package com.example.humansvszombiesbackend.model.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class UserDTO implements Serializable {

    private String id;

    private String firstName;

    private String lastName;

}
