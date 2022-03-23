package com.example.humansvszombiesbackend.service;

import com.example.humansvszombiesbackend.enums.GameState;

public interface GameStateService {

    GameState nextState(GameState state);

}
