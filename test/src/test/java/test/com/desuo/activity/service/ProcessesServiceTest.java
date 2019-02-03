package test.com.desuo.activity.service;

import com.desuo.activity.repository.TenantRepository;
import com.desuo.activity.service.ProcessesService;
import com.desuo.activity.service.TenantService;
import com.desuo.activity.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.activiti.engine.*;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import test.com.desuo.activity.TestBase;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Fuyuanwu
 * @date 2019/1/28 16:31
 */
public class ProcessesServiceTest extends TestBase {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProcessesService processesService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private TenantService tenantService;
    @Autowired
    private TenantRepository tenantRepository;

    @Test
    public void testDeploy() throws IOException, InterruptedException {
        String tenantId = "tenantId_aaa1";
        String name = "name_aaa1";
        String key = "key_aaa1";
        byte[] bytes = FileCopyUtils.copyToByteArray(new File("src/test/resources/processes/test2.bpmn"));
        ProcessDefinition processDefinition = processesService.deployProcess(tenantId, name, key, bytes);

        String processDefinitionId = processDefinition.getId();

        List<IdentityLink> identityLinks = repositoryService.getIdentityLinksForProcessDefinition(processDefinitionId);
        if (identityLinks != null) {
            logger.info("流程启动人：" + JsonUtil.getInstance().writeValueAsString(identityLinks));
        }

        logger.info("流程定义ID:" + processDefinition.getId());//流程定义的key+版本+随机生成数
        logger.info("流程定义的名称:" + processDefinition.getName());//对应helloworld.bpmn文件中的name属性值
        logger.info("流程定义的key:" + processDefinition.getKey());//对应helloworld.bpmn文件中的id属性值
        logger.info("流程定义的版本:" + processDefinition.getVersion());//当流程定义的key值相同的相同下，版本升级，默认1
        logger.info("资源名称bpmn文件:" + processDefinition.getResourceName());
        logger.info("资源名称png文件:" + processDefinition.getDiagramResourceName());
        logger.info("部署对象ID：" + processDefinition.getDeploymentId());
    }

    @Test
    public void testStartProcess() throws IOException, InterruptedException {
        String tenantId = "tenantId_aaa1";
        String definitionKey = "PROCESS_1";
        String userId = "a";

        ProcessInstance processInstance = processesService.startProcessByDefinitionKey(tenantId, definitionKey, userId, null);
        System.out.println("");
    }

    @Test
    public void testGetProcessNodes() throws JsonProcessingException {
        String processDefinitionId = "PROCESS_1:9:32746765-2533-11e9-881d-00ff3108f5f0";
        List list = processesService.getProcessNodes(processDefinitionId);
        System.out.println(JsonUtil.getInstance().writeValueAsString(list));
    }

    private void printProcessInstance(ProcessDefinition processDefinition) {
        logger.info("流程定义ID:" + processDefinition.getId());//流程定义的key+版本+随机生成数
        logger.info("流程定义的名称:" + processDefinition.getName());//对应helloworld.bpmn文件中的name属性值
        logger.info("流程定义的key:" + processDefinition.getKey());//对应helloworld.bpmn文件中的id属性值
        logger.info("流程定义的版本:" + processDefinition.getVersion());//当流程定义的key值相同的相同下，版本升级，默认1
        logger.info("资源名称bpmn文件:" + processDefinition.getResourceName());
        logger.info("资源名称png文件:" + processDefinition.getDiagramResourceName());
        logger.info("部署对象ID：" + processDefinition.getDeploymentId());
    }
}
