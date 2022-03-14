package com.example.humansvszombiesbackend.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

public enum GameState {
    CONFIGURATION("CONFIGURATION"),
    REGISTRATION("REGISTRATION"),
    IN_PROGRESS("IN_PROGRESS"),
    COMPLETE("COMPLETE");

    private String serialized;

    GameState(String serialized) { this.serialized = serialized; }

    @JsonCreator
    public static GameState deserialize(String serialized) {
        return Stream.of(GameState.values()).filter(targetEnum -> targetEnum.serialized.equals(serialized)).findFirst().orElse(null);
    }

    @JsonValue
    public String serialize() {
        return serialized;
    }

}
