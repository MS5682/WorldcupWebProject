package com.world.cup.repository;

import com.world.cup.entity.User;
import com.world.cup.entity.Worldcup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface WorldcupRepository extends JpaRepository<Worldcup, Long>, WorldcupListRepository {
    @Query("select w from Worldcup w where w.worldcupNum = :worldcupNum")
    Object getWorldcupByWorldcupNum(@Param("worldcupNum") int worldcupNum);

    @Transactional
    @Modifying
    @Query("UPDATE Worldcup " +
            "SET disclosure = CASE " +
            "    WHEN disclosure = 1 THEN 0 " +
            "    WHEN disclosure = 0 THEN 1 " +
            "END " +
            "WHERE worldcupNum = :worldcupNum")
    int updateDisclosureByWorldcupNum(@Param("worldcupNum")int worldcupNum);

}
