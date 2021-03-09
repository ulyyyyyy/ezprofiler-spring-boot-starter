package com.lilithqa.ezprofiler.config;

import com.lilithqa.ezprofiler.controller.EzProfilerController;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.HandlerMapping;
import com.lilithqa.ezprofiler.mapping.PropertySourcedRequestMappingHandlerMapping;

/**
 * @author 黑黑
 * @apiNote ezProfiler配置类
 * @date 2021-03-04
 */
@Configuration
@ComponentScan(basePackages =
        {
                "com.lilithqa.ezprofiler.mapping",
                "com.lilithqa.ezprofiler.controller",
                "com.lilithqa.ezprofiler.scanner",
                "com.lilithqa.ezprofiler.repository"})
public class EzProfilerConfiguration {

    @Autowired
    private EzProfilerProperties properties;

    @Bean
    public HandlerMapping ezProfilerControllerMapping(Environment environment) {
        return new PropertySourcedRequestMappingHandlerMapping(environment, new EzProfilerController(properties));
    }

}
