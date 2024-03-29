package com.world.cup.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserListRepository {
    Page<Object[]> getMemberListPage(String type, String keyword, Pageable pageable);
}
