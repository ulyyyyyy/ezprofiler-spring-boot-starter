package com.lilithqa.ezprofiler.config;

import com.lilithqa.ezprofiler.controller.EzProfilerController;
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
// 该注解默认会扫描指定包下所有的配置类
@ComponentScan(basePackages =
        {
                "com.lilithqa.ezprofiler.mapping",
                "com.lilithqa.ezprofiler.controller",
                "com.lilithqa.ezprofiler.scanner"})

public class EzProfilerConfiguration {

    @Autowired
    private EzProfilerProperties properties;

    @Bean
    public HandlerMapping ezProfilerControllerMapping(Environment environment) {
        return new PropertySourcedRequestMappingHandlerMapping(environment, new EzProfilerController(properties));
    }
}
