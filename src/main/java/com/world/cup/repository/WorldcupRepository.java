package com.world.cup.repository;

import com.world.cup.entity.Worldcup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorldcupRepository extends JpaRepository<Worldcup, Long>, WorldcupListRepository {

}
