package com.example.humansvszombiesbackend.service;

import com.example.humansvszombiesbackend.enums.GameState;
import org.springframework.stereotype.Service;

@Service
public class GameStateServiceImpl implements GameStateService {

    @Override
    public GameState nextState(GameState currentState) {
        if (currentState.ordinal() == GameState.values().length - 1)
            return currentState;

        return GameState.values()[currentState.ordinal() + 1];
    }

    @Override
    public Boolean isJoinable(GameState state) {
        return GameState.REGISTRATION.equals(state);
    }

}
