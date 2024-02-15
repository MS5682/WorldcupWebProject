package com.world.cup.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface UserListRepository {
    Page<Object[]> getMemberListPage(String type, String keyword, Pageable pageable);
}
