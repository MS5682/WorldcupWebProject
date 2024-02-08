package com.world.cup.repository;

import com.world.cup.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {
    Optional<User> findByIdAndPassword(String id, String password);
    boolean existsById(String id);
    boolean existsByEmail(String email);
}
