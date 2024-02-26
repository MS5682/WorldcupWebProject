package com.world.cup.repository;

import com.world.cup.entity.Choice;
import com.world.cup.entity.Proceed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProceedRepository extends JpaRepository<Proceed, Integer> {
    Proceed findByUser_IdAndChoice(String userId, Choice choice);

    @Query("select p from Proceed p where p.user.id = :userid and p.worldcup.worldcupNum = :worldcupNum")
    List<Proceed> endPlayResult(String userid, int worldcupNum);

//    @Query("select p from Proceed p where p.user.id = :userid and p.worldcup.worldcupNum = :worldcupNum")
//    List<Proceedinterface> loadCandi(String userid, int worldcupNum);
//
    List<Proceed> findProceedsByUserIdAndWorldcup_WorldcupNum(String userId, int worldcupNum);
}
