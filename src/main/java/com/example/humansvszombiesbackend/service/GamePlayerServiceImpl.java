package com.example.humansvszombiesbackend.service;

import com.example.humansvszombiesbackend.model.dbo.Game;
import com.example.humansvszombiesbackend.model.dbo.Player;
import com.example.humansvszombiesbackend.model.dto.Response;
import com.example.humansvszombiesbackend.repository.GameRepository;
import com.example.humansvszombiesbackend.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class GamePlayerServiceImpl implements GamePlayerService {

    private final GameRepository games;
    private final PlayerRepository players;
    private final BiteCodeService biteCodes;
    private final GameStateService gameStates;

    @Override
    public Response<Player> createPlayer(Integer gameId, UUID userId, String name) {
        return games.findById(gameId).map(
                game -> {

                    if (!gameStates.isJoinable(game.getState()))
                        return new Response<Player>("Game is not currently open for registration");

                    boolean duplicate = game.getPlayers().stream()
                            .anyMatch(player -> player.getUserId().equals(userId));

                    if (duplicate)
                        return new Response<Player>("User is already added to the game");

                    Player player = Player.builder()
                            .userId(userId)
                            .name(name)
                            .currentGame(game)
                            .biteCode(biteCodes.generate(game.getId()))
                            .build();

                    game.getPlayers().add(player);

                    return new Response<>(players.save(player));
                }
        ).orElse(new Response<>("Game not found"));
    }

    @Override
    public Response<Player> updatePlayer(Integer playerId, Player player) {
        return players.findById(playerId).map(foundPlayer -> {
            player.setId(playerId);
            return new Response<>(players.save(player));
        }).orElse(new Response<>("Player with id " + playerId + " not found"));
    }

    @Override
    public Optional<Player> findPlayer(Integer gameId, Integer playerId) {
        Optional<Game> game = games.findById(gameId);

        // Map found game to player
        return game.flatMap(foundGame -> foundGame.getPlayers().stream()
                .filter(p -> p.getId().equals(playerId)) // Filter for playerId
                .findFirst());  // Find the first matching player;
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
