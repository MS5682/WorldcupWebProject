package com.world.cup.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChoiceListRepository {
    Page<Object[]> getChoiceList(String type, String keyword, int worldcupNum, Pageable pageable);

}
