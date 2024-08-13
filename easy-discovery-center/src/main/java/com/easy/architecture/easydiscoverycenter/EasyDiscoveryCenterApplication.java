package com.easy.architecture.easydiscoverycenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author yanghai10
 * @ClassName
 * @Description
 * @date 2024/7/28 19:09
 */
@EnableEurekaServer
@SpringBootApplication
public class EasyDiscoveryCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyDiscoveryCenterApplication.class, args);
    }

}
