package com.github.houbb.sensitive.word.admin.dal.entity.po;

import com.github.houbb.sensitive.word.admin.dal.entity.Word;

/**
 * <p>
 * 敏感词表-分页查询对象
 * </p>
 *
 * @author dh
 * @since 2024-02-05
 */
public class WordPagePo extends Word {

    /**
    * 分页大小
    */
    private Integer limit;

    /**
    * 当前页码
    */
    private Integer page;

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "WordPagePo{" +
        "limit=" + limit +
        ", page=" + page +
        "} " + super.toString();
    }

}