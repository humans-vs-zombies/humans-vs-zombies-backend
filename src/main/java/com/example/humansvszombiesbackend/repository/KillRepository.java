package com.example.humansvszombiesbackend.repository;

import com.example.humansvszombiesbackend.model.dbo.Kill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KillRepository extends JpaRepository<Kill, Integer> {

}
