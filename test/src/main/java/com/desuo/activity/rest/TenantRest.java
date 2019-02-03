package com.desuo.activity.rest;

import com.desuo.activity.common.rest.BaseRest;
import com.desuo.activity.entity.Tenant;
import com.desuo.activity.repository.TenantRepository;
import com.desuo.activity.service.TenantService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Fuyuanwu
 * @date 2019/1/28 15:04
 */
@RestController
@RequestMapping("/tenants")
@CrossOrigin
@Api("租户相关接口")
public class TenantRest extends BaseRest<TenantService, TenantRepository, Tenant, String> {
}
