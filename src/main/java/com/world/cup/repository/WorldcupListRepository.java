package com.world.cup.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WorldcupListRepository {
    Page<Object[]> getWorldcupList(String type, String keyword, Pageable pageable);

}
