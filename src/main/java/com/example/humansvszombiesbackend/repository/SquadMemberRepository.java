package com.example.humansvszombiesbackend.repository;

import com.example.humansvszombiesbackend.model.dbo.SquadMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SquadMemberRepository extends JpaRepository<SquadMember, Integer> {
}
