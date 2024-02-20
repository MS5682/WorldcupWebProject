package com.world.cup.repository;

import com.world.cup.entity.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String>,UserListRepository {
    Optional<User> findByIdAndPassword(String id, String password);

    Optional<User> findByEmail(String email);

    Optional<User> findById(String id);

    Optional<User> findByIdAndEmail(String id,String email);



    boolean existsById(String id);
    boolean existsByEmail(String email);


    @Query("select u from User u where u.id = :id")
    User getUserById(@Param("id")String id);

}
