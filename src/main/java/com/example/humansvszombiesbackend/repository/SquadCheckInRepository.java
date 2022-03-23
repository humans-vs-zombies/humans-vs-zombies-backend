package com.example.humansvszombiesbackend.repository;

import com.example.humansvszombiesbackend.model.dbo.SquadCheckIn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SquadCheckInRepository extends JpaRepository<SquadCheckIn, Integer> {
}
