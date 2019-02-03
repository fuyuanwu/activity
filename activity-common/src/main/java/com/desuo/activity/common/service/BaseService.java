package com.desuo.activity.common.service;

import com.desuo.activity.common.repository.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Fuyuanwu
 * @date 2019/1/31 15:48
 */
public class BaseService<R extends BaseRepository, T, ID> {
    @Autowired(required = false)
    protected R repository;

    public R getRepository() {
        return repository;
    }
}