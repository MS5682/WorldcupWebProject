package com.world.cup.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WorldcupListRepository {
    Page<Object[]> getWorldcupList(String type, String keyword, Pageable pageable);

    Page<Object[]> getPublicWorldcupList(String type, String keyword, Pageable pageable);

    Page<Object[]> getPrivateWorldcupList(String type, String keyword, Pageable pageable);

}
