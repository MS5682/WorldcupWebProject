package com.world.cup.repository;

import com.world.cup.entity.Choice;
import com.world.cup.entity.Proceed;
import com.world.cup.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProceedRepository extends JpaRepository<Proceed, Integer> {
    Proceed findByUserAndChoice(User userId, Choice choice);
}
