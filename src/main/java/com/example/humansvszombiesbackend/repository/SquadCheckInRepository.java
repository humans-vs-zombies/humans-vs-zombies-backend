package com.example.humansvszombiesbackend.repository;

import com.example.humansvszombiesbackend.model.dbo.SquadCheckIn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SquadCheckInRepository extends JpaRepository<SquadCheckIn, Integer> {

    List<SquadCheckIn> findAllByGameIdAndSquadId(Integer gameId, Integer squadId);

}
