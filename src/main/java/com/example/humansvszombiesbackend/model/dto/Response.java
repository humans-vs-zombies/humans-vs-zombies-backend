package com.example.humansvszombiesbackend.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Response<T> {
    private boolean success = true;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String error;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T payload;

    public Response(T payload) { this.payload = payload; }

    public Response(String error) {
        this.success = false;
        this.error = error;
    }
}
