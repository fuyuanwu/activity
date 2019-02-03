package com.desuo.activity.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Fuyuanwu
 * @date 2019/1/31 15:31
 */
@Entity
@Table(name = "t_tenant", schema = "activity")
public class Tenant implements Serializable {
    private String tenantId;
    private String tenantName;
    private String webhookUrl;

    @Id
    @Column(name = "tenant_id", nullable = false)
    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @Basic
    @Column(name = "tenant_name", nullable = true)
    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    @Basic
    @Column(name = "webhook_url", nullable = true)
    public String getWebhookUrl() {
        return webhookUrl;
    }

    public void setWebhookUrl(String webhookUrl) {
        this.webhookUrl = webhookUrl;
    }
}
