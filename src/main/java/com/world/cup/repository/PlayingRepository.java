package com.world.cup.repository;

import com.world.cup.Choiceinterface;
import com.world.cup.entity.Choice;
import com.world.cup.entity.Worldcup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlayingRepository extends JpaRepository<Choice, Integer> {
    @Query("select c from Choice c left join Worldcup w on c.worldcup.worldcupNum = w.worldcupNum where c.worldcup.worldcupNum = :worldCupID")
    List<Choiceinterface> selectCandi(@Param("worldCupID") int worldCupID);

    @Query("select w from Worldcup w where w.worldcupNum = :worldCupID")
    Worldcup worldCupTitle(@Param("worldCupID") int worldCupID);
}
