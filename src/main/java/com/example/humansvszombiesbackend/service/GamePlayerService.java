package com.example.humansvszombiesbackend.service;

import com.example.humansvszombiesbackend.model.dbo.Player;
import com.example.humansvszombiesbackend.model.dto.Response;

import java.util.UUID;

public interface GamePlayerService {

    Response<Player> createPlayer(Integer gameId, UUID userId);

    boolean addToGame(Integer gameId, Integer playerId);

    boolean removeFromGame(Integer gameId, Integer playerId);

}
