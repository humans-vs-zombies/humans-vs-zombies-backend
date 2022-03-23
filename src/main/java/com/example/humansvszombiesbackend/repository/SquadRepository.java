package com.example.humansvszombiesbackend.repository;

import com.example.humansvszombiesbackend.model.dbo.Squad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SquadRepository extends JpaRepository<Squad, Integer> {
    List<Squad> findAllByGameId(Integer gameId);

    Optional<Squad> findByGameIdAndId(Integer gameId, Integer squadId);
}
