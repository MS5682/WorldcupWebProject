package com.world.cup.repository;

import com.world.cup.entity.Worldcup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorldcupRepository extends JpaRepository<Worldcup, Integer>, WorldcupListRepository {
    @Query("select w from Worldcup w where w.worldcupNum = :worldcupNum")
    Object getWorldcupByWorldcupNum(@Param("worldcupNum") int worldcupNum);

}
