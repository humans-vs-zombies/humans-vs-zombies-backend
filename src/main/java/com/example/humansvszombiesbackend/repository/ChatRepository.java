package com.example.humansvszombiesbackend.repository;

import com.example.humansvszombiesbackend.model.dbo.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Integer> {

    List<Chat> findAllByGameIdAndIsHumanGlobalTrue(Integer gameId);
    List<Chat> findAllByGameIdAndIsZombieGlobalTrue(Integer gameId);
    List<Chat> findAllByGameIdAndSquadId(Integer gameId, Integer squadId);

}
