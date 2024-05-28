package com.github.houbb.sensitive.word.admin.dal.entity;

import java.util.List;

public class BasePageInfo<T> {
    private long total;
    private List<T> data;
    private long totalPages;

    public BasePageInfo() {
    }

    public long getTotal() {
        return this.total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getData() {
        return this.data;
    }

    public void setData(List<T> list) {
        this.data = list;
    }

    public long getTotalPages() {
        return this.totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    public String toString() {
        return "BasePageInfo{total=" + this.total + ", data=" + this.data + ", totalPages=" + this.totalPages + '}';
    }
}