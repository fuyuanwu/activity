package com.desuo.activity.rest;

import com.desuo.activity.service.ProcessesService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Fuyuanwu
 * @date 2019/1/28 15:04
 */
@RestController
@RequestMapping("/processes")
@CrossOrigin
@Api("流程定义相关接口")
public class ProcessesRest {
    private final ProcessesService processesService;

    @Autowired
    public ProcessesRest(ProcessesService processesService) {
        this.processesService = processesService;
    }



}
