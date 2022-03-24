package com.example.humansvszombiesbackend.repository;

import com.example.humansvszombiesbackend.model.dbo.SquadMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SquadMemberRepository extends JpaRepository<SquadMember, Integer> {
    Optional<SquadMember> findByGameIdAndSquadIdAndPlayerId(Integer gameId, Integer squadId, Integer playerId);

    Optional<SquadMember> findByGameIdAndPlayerId(Integer gameId, Integer playerId);
}
