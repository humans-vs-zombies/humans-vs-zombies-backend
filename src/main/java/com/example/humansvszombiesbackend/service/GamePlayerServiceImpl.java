package com.example.humansvszombiesbackend.service;

import com.example.humansvszombiesbackend.model.dbo.Player;
import com.example.humansvszombiesbackend.model.dto.Response;
import com.example.humansvszombiesbackend.repository.GameRepository;
import com.example.humansvszombiesbackend.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class GamePlayerServiceImpl implements GamePlayerService {

    private final GameRepository games;
    private final PlayerRepository players;
    private final BiteCodeService biteCodes;

    @Override
    public Response<Player> createPlayer(Integer gameId, UUID userId) {
        return games.findById(gameId).map(
                game -> {
                    boolean duplicate = game.getPlayers().stream()
                            .anyMatch(player -> player.getUserId().equals(userId));

                    if (duplicate)
                        return new Response<Player>("User is already added to the game");

                    Player player = Player.builder()
                            .userId(userId)
                            .currentGame(game)
                            .biteCode(biteCodes.generate())
                            .build();

                    game.getPlayers().add(player);

                    return new Response<>(players.save(player));
                }
        ).orElse(new Response<>("Game not found"));
    }

    @Override
    public boolean addToGame(Integer gameId, Integer playerId) {
        return false;
    }

    @Override
    public boolean removeFromGame(Integer gameId, Integer playerId) {
        return false;
    }

}
