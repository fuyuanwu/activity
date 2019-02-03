package com.desuo.activity.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger2 API文档配置
 *
 * @author fuyuanwu
 */
@Configuration
public class Swagger2Config implements WebMvcConfigurer {
    @Value("${swagger2.enable: false}")
    private boolean swagger2Enable;
    @Value("${project.version:''}")
    private String projectVersion;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (swagger2Enable) {
            // 允许访问swagger2的静态文件
            registry.addResourceHandler("/swagger-ui.html")
                    .addResourceLocations("classpath:/META-INF/resources/");

            registry.addResourceHandler("/webjars/**")
                    .addResourceLocations("classpath:/META-INF/resources/webjars/");
        }
    }

    @Bean
    public Docket createRestApi() {
        List<Parameter> pars = new ArrayList<>();

        // 定义一些公共参数
        // ParameterBuilder tokenPar = new ParameterBuilder();
        // tokenPar.name("token").description("鉴权令牌")
        //         .modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        // pars.add(tokenPar.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.desuo"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars)
                .enable(swagger2Enable);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("基本信息服务")
                .description("查询员工、部门、角色、投资者基本信息的服务")
                .version(projectVersion)
                .build();
    }
}