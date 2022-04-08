package com.fullstuckcode.fullstuckcode.model.response;

import java.util.List;

public class PageResponse<T> {

    private final List<T> content;
    private final Long count;
    private final Integer totalPage;
    private final Integer page;
    private final Integer size;

    public PageResponse(List<T> content, Long count, Integer totalPage, Integer page, Integer size) {
        this.content = content;
        this.count = count;
        this.totalPage = totalPage;
        this.page = page;
        this.size = size;
    }

    public List<T> getContent() {
        return content;
    }

    public Long getCount() {
        return count;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public Integer getPage() {
        return page;
    }

    public Integer getSize() {
        return size;
    }
}
