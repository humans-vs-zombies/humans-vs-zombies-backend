package com.example.humansvszombiesbackend.service;

import com.example.humansvszombiesbackend.model.dbo.Game;
import com.example.humansvszombiesbackend.model.dbo.Player;
import com.example.humansvszombiesbackend.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class BitecodeServiceImpl implements BiteCodeService {
    private static final int BITE_CODE_LENGTH = 6;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final Random random = new SecureRandom();

    private final GameRepository games;

    @Override
    public String generate(Integer gameId) {
        Game game = games.getById(gameId);

        String biteCode = null;
        boolean unique = false;

        while(!unique) {
            biteCode = "";

            // Generate random bite code
            for(int i = 0; i < BITE_CODE_LENGTH; i++) {
                biteCode += CHARACTERS.charAt(random.nextInt(CHARACTERS.length()));
            }

            // Check if bite code already exists for any player in game
            String finalBiteCode = biteCode;
            unique = !game.getPlayers().stream().anyMatch(player -> player.getBiteCode() == finalBiteCode);
        }

        return biteCode;
    }

}
