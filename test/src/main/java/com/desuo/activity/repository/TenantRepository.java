package com.desuo.activity.repository;

import com.desuo.activity.common.repository.BaseRepository;
import com.desuo.activity.entity.Tenant;

import java.util.List;

/**
 * @author Fuyuanwu
 * @date 2019/1/31 13:55
 */
public interface TenantRepository extends BaseRepository<Tenant, String> {
    List<Tenant> findByTenantName(String tenantName);
}
