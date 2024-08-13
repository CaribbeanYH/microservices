package com.easy.architecture.easymicroserviceribbon.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author yanghai10
 * @ClassName
 * @Description
 * @date 2024/8/12 17:37
 */
@Configuration
public class RibbonConfig {

    @Bean("restTemplate")
    @LoadBalanced
    public RestTemplate getRestTemplateInstance() {
        return new RestTemplate();
    }
}
