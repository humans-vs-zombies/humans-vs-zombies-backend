package com.example.humansvszombiesbackend.service;

import com.example.humansvszombiesbackend.model.dbo.Player;
import com.example.humansvszombiesbackend.model.dto.Response;

import java.util.Optional;
import java.util.UUID;

public interface GamePlayerService {

    Response<Player> createPlayer(Integer gameId, UUID userId, String name);

    Response<Player> updatePlayer(Integer playerId, Player player);

    Optional<Player> findPlayer(Integer gameId, Integer playerId);

    boolean addToGame(Integer gameId, Integer playerId);

    boolean removeFromGame(Integer gameId, Integer playerId);

}
