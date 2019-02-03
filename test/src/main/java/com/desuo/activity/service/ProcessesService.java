package com.desuo.activity.service;

import com.desuo.activity.repository.TenantRepository;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.Task;
import org.activiti.engine.*;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Fuyuanwu
 * @date 2019/1/28 15:55
 */
@Service
public class ProcessesService {
    final RuntimeService runtimeService;
    final TaskService taskService;
    final RepositoryService repositoryService;
    final HistoryService historyService;
    final ProcessEngine processEngine;
    final TenantService tenantService;
    final TenantRepository tenantRepository;


    @Autowired
    public ProcessesService(RuntimeService runtimeService, TaskService taskService, RepositoryService repositoryService, ProcessEngine processEngine, HistoryService historyService, TenantService tenantService, TenantRepository tenantRepository) {
        this.runtimeService = runtimeService;
        this.taskService = taskService;
        this.repositoryService = repositoryService;
        this.processEngine = processEngine;
        this.historyService = historyService;
        this.tenantService = tenantService;
        this.tenantRepository = tenantRepository;
    }

    /**
     * 发布/部署一个流程
     *
     * @param tenantId 租户id
     * @param name     本次发布的名称
     * @param key      本次发布的key
     * @param bytes    本次发布的流程定义文件
     * @return 流程定义实例
     */
    public ProcessDefinition deployProcess(String tenantId, String name, String key, byte[] bytes) {
        // resourceName 命名规则需要满足 "**.bpmn20.xml", "**.bpmn"。 见 {@link org.activiti.spring.boot.ActivitiProperties#processDefinitionLocationSuffixes}
        String resourceName = Objects.hash(tenantId, name, key) + ".bpmn";
        Deployment deployment = repositoryService.createDeployment().addBytes(resourceName, bytes).name(name).key(key).tenantId(tenantId).deploy();

        return repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();
    }

    /**
     * 通过 processDefinitionKey 启动流程。启动的是这个Key最后一次发布的那个流程
     *
     * @param tenantId             租户id
     * @param processDefinitionKey 流程定义key
     * @param variables            启动流程的参数
     * @return 流程实例
     */
    public ProcessInstance startProcessByDefinitionKey(String tenantId, String processDefinitionKey, String userId, Map<String, Object> variables) {
        ProcessDefinition processDefinition = ((ProcessEngineConfigurationImpl) processEngine.getProcessEngineConfiguration())
                .getDeploymentManager().findDeployedLatestProcessDefinitionByKeyAndTenantId(processDefinitionKey, tenantId);

        repositoryService.getIdentityLinksForProcessDefinition(processDefinition.getId());

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKeyAndTenantId(processDefinitionKey, variables, tenantId);
        String processInstanceId = processInstance.getProcessInstanceId();

        return null;
    }
/*
git init
git add README.md
git commit -m "first commit"



git remote add origin https://github.com/fuyuanwu/activity.git
git push -u origin master

 */
    /**
     * 通过 processDefinitionId 启动流程
     *
     * @param tenantId            租户id
     * @param processDefinitionId 流程定义id
     * @param variables           启动流程的参数
     * @return 流程实例
     */
    public ProcessInstance startProcessByDefinitionId(String tenantId, String processDefinitionId, String userId, Map<String, Object> variables) {
        ProcessDefinition processDefinition = repositoryService.getProcessDefinition(processDefinitionId);

        Assert.notNull(processDefinition, String.format("无法通过该流程id查询到流程定义[processDefinitionId: %S]", processDefinitionId));
        Assert.isTrue(Objects.equals(tenantId, processDefinition.getTenantId()),
                String.format("无法通过该流程id查询到该租户下的流程定义[tenantId: %s, processDefinitionId: %S]", tenantId, processDefinitionId));

        return runtimeService.startProcessInstanceById(processDefinitionId, variables);
    }

    /**
     * @param processDefinitionId
     * @return
     */
    public List<FlowElement> getProcessNodes(String processDefinitionId) {
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);

        FlowElement initialFlowElement = bpmnModel.getMainProcess().getInitialFlowElement();
        Comparator comparator = Comparator.naturalOrder();

        List<FlowElement> flowElements = bpmnModel.getMainProcess().getFlowElements().stream().filter(e -> e instanceof Task).sorted((e1, e2) -> comparator.compare(e1.getId(), e2.getId())).collect(Collectors.toList());

        flowElements.add(0, initialFlowElement);
        return flowElements;
    }
}
