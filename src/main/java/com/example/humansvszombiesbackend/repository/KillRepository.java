package com.example.humansvszombiesbackend.repository;

import com.example.humansvszombiesbackend.model.dbo.Kill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KillRepository extends JpaRepository<Kill, Integer> {

    Optional<Kill> findByGameIdAndId(Integer gameId, Integer id);

}
