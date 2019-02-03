package com.desuo.activity.common.web;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

/**
 * @author Fuyuanwu
 * @date 2019/1/31 17:19
 */
@JsonIgnoreProperties(value = {"pageable", "sort"})
public class PageResponse<T> extends PageImpl<T> implements Serializable {
    private static final long serialVersionUID = 13453534L;

    public PageResponse(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public PageResponse(List<T> content) {
        super(content);
    }

    public static <T> PageResponse<T> of(PageImpl<T> page) {
        return new PageResponse<>(page.getContent(), page.getPageable(), page.getTotalElements());
    }

    public static <T> PageResponse<T> of(PageImpl page, List<T> content) {
        if (page.getSize() == 0) {
            return new PageResponse<>(content);
        }

        return new PageResponse<>(content, PageRequest.of(page.getNumber(), page.getSize(), page.getSort()), page.getTotalElements());
    }

    public PageImpl<T> toPageImpl() {
        if (getSize() == 0) {
            return new PageImpl<>(getContent());
        }

        return new PageImpl<>(getContent(), PageRequest.of(getNumber(), getSize(), getSort()), getTotalElements());
    }
}
