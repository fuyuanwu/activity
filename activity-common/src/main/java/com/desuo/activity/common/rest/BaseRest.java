package com.desuo.activity.common.rest;

import com.desuo.activity.common.repository.BaseRepository;
import com.desuo.activity.common.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author wanghaobin
 * @create 2017-06-15 8:48
 */
public abstract class BaseRest<S extends BaseService<R, T, ID>, R extends BaseRepository<T, ID>, T, ID> {
    @Autowired
    protected HttpServletRequest request;
    @Autowired(required = false)
    protected S service;
    @Autowired(required = false)
    protected R repository;

    /**
     * 新增
     *
     * @param entity
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<T> add(@RequestBody T entity) {
        return ResponseEntity.ok(repository.save(entity));
    }

    /**
     * 查询
     *
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<T>> all() {
        return ResponseEntity.ok(repository.findAll());
    }

    /**
     * 分页查询
     *
     * @return
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Page<T>> list(T query, @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(repository.findAll(Example.of(query), pageable));
    }

    /**
     * 根据ID查询
     *
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<T> get(@PathVariable ID id) {
        return ResponseEntity.of(repository.findById(id));
    }

    /**
     * 根据ID修改
     *
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<T> update(@RequestBody T entity) {
        return ResponseEntity.ok(repository.save(entity));
    }

    /**
     * 根据ID删除
     *
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity remove(@PathVariable ID id) {
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
