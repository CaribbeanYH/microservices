package com.easy.architecture.easymicroserviceribbon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author yanghai10
 * @ClassName
 * @Description
 * @date 2024/7/28 19:09
 */
@LoadBalancerClient(name = "easy-microservice-order")
@EnableEurekaClient
@SpringBootApplication
public class EasyMicroserviceRibbonApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyMicroserviceRibbonApplication.class, args);
    }

}
