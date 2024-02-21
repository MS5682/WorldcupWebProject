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
    private int commentPage;
    private int commentSize;
    private int requestPage;
    private int requestSize;
    private String type;
    private String keyword;
    private int order;
    private String userId;
    private Byte disclosure;
    private Integer choiceNum;
    private int worldcupNum;
    private Integer limit;
    private int commentType;
    private String pageLoc;

    public PageRequestDTO(){
        this.page = 1;
        this.size = 9;
        this.commentPage = 1;
        this.commentSize = 10;
        this.requestPage = 1;
        this.requestSize = 10;
    }

    public Pageable getPageable(Sort sort) {
        return PageRequest.of(page - 1, size, sort);
    }
    public Pageable getCommentPageable(Sort sort) { return PageRequest.of(commentPage - 1, commentSize, sort);}
    public Pageable getRequestPageable(Sort sort) {
        return PageRequest.of(requestPage - 1, requestSize, sort);
    }
}
