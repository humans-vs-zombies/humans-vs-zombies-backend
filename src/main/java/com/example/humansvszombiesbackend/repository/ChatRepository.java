package com.example.humansvszombiesbackend.repository;

import com.example.humansvszombiesbackend.model.dbo.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Integer> {
}
