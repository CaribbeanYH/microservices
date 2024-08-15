package com.easy.architecture.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author yanghai10
 * @ClassName
 * @Description
 * @date 2024/7/28 19:09
 */
@EnableEurekaClient
@ComponentScan(basePackages = {"com.easy.architecture"})
@SpringBootApplication
public class EasyMicroserviceGateWayApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyMicroserviceGateWayApplication.class, args);
    }
}
