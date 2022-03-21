package com.example.humansvszombiesbackend.repository;

import com.example.humansvszombiesbackend.model.dbo.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {

    List<Player> findPlayersByCurrentGameId(Integer id);

    Optional<Player> findPlayersByCurrentGameIdAndId(Integer gameId, Integer id);

}
