package com.world.cup.repository;

import com.world.cup.entity.Choice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChoiceRepository extends JpaRepository<Choice, Integer> {
    @Query("select c from Choice c where c.worldcup.worldcupNum = :worldcupNum")
    List<Choice> getChoiceByWorldcupNum(@Param("worldcupNum") int worldcupNum);

}
