package com.world.cup.repository;

import com.world.cup.entity.Worldcup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WorldcupRepository extends JpaRepository<Worldcup, Long> {

    @Query("SELECT w, c1.link AS link1, c1.name AS name1, c1.type AS type1, c2.link AS link2, c2.name AS name2, c2.type AS type2 " +
            "FROM Worldcup w " +
            "JOIN Choice c1 ON c1.worldcup = w " +
            "JOIN Choice c2 ON c2.worldcup = w AND c1.choiceNum < c2.choiceNum " +
            "GROUP BY w.worldcupNum " +
            "ORDER BY w.worldcupNum")
    List<Object[]> getWorldcupWithChoices();

}
