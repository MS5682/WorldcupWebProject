package com.world.cup.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Builder
@AllArgsConstructor
@Data
public class PageRequestDTO {
    private int page;
    private int size;
    private String type;
    private String keyword;
    private int order;
    private String userId;
    private Byte disclosure;
    private int worldcupNum;
    private Integer limit;

    public PageRequestDTO(){
        this.page = 1;
        this.size = 9;
    }

    public Pageable getPageable(Sort sort) {
        return PageRequest.of(page - 1, size, sort);
    }
}
