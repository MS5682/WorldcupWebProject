package com.world.cup.repository;

import com.world.cup.entity.User;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {
    Optional<User> findByIdAndPassword(String id, String password);

    Optional<User> findByEmail(String email);

    Optional<User> findById(String id);

    Optional<User> findByIdAndEmail(String id,String email);

    boolean existsById(String id);
    boolean existsByEmail(String email);
}
