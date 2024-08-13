package com.easy.architecture.easymicroservicefegin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author yanghai10
 * @ClassName
 * @Description
 * @date 2024/7/28 19:09
 */
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.easy.architecture"})
@ComponentScan(basePackages = {"com.easy.architecture"})
@SpringBootApplication
public class EasyMicroserviceFeignApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyMicroserviceFeignApplication.class, args);
    }

}
