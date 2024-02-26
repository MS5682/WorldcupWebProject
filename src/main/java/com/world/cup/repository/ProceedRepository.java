package com.world.cup.repository;

import com.world.cup.Proceedinterface;
import com.world.cup.entity.Choice;
import com.world.cup.entity.Proceed;
import com.world.cup.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProceedRepository extends JpaRepository<Proceed, Integer> {
    Proceed findByUser_IdAndChoice(String userId, Choice choice);

    @Query("select p from Proceed p where p.user.id = :userid and p.worldcup.worldcupNum = :worldcupNum")
    List<Proceed> endPlayResult(String userid, int worldcupNum);

//    @Query("select p from Proceed p where p.user.id = :userid and p.worldcup.worldcupNum = :worldcupNum")
//    List<Proceedinterface> loadCandi(String userid, int worldcupNum);
//
//    @Query("select p.choice.choiceNum from Proceed p where p.proceedNum = :proceedNum")
//    int findchoicenum(int proceedNum);

//    Choice update
}
