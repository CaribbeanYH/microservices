package com.easy.architecture.easymicroserviceorder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author yanghai10
 * @ClassName
 * @Description
 * @date 2024/7/28 19:09
 */
@EnableEurekaClient
@SpringBootApplication
public class EasyMicroserviceOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyMicroserviceOrderApplication.class, args);
    }

}
