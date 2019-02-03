package com.desuo.activity.configuration;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Fuyuanwu
 * @date 2019/1/29 17:52
 */

@Configuration
public class ActivityConfig {

    @Bean
    public org.activiti.spring.boot.ProcessEngineConfigurationConfigurer initProcessEngineConfigurationConfigurer() {
        return new ProcessEngineConfigurationConfigurer();
    }

    private class ProcessEngineConfigurationConfigurer implements org.activiti.spring.boot.ProcessEngineConfigurationConfigurer {
        @Override
        public void configure(SpringProcessEngineConfiguration processEngineConfiguration) {
            List<ActivitiEventListener> activitiEventListeners = processEngineConfiguration.getEventListeners();

            processEngineConfiguration.getUserGroupManager();

            if (activitiEventListeners == null) {
                activitiEventListeners = new ArrayList<>();
                activitiEventListeners.add(new ActivityGlobalEventListener());
                processEngineConfiguration.setEventListeners(activitiEventListeners);
            }

            System.out.println("ActivityConfig:ProcessEngineConfigurationConfigurer#configure");
        }
    }

    public static class ActivityGlobalEventListener implements ActivitiEventListener {

        @Override
        public void onEvent(ActivitiEvent event) {
            event.getExecutionId();
            System.out.println("ActivityConfig:ActivityGlobalEventListener#onEvent");
        }

        @Override
        public boolean isFailOnException() {
            return false;
        }
    }
}
