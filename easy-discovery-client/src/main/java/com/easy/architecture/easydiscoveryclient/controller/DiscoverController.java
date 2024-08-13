package com.easy.architecture.easydiscoveryclient.controller;

import com.alibaba.fastjson.JSON;
import com.netflix.discovery.DiscoveryClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yanghai10
 * @ClassName
 * @Description
 * @date 2024/8/12 15:50
 */
@Slf4j
@RestController
public class DiscoverController {

    @Resource
    private EurekaDiscoveryClient eurekaDiscoveryClient;

    @GetMapping("/discovery")
    public String discovery() {
        List<String> services = eurekaDiscoveryClient.getServices();
        log.info("注册的服务内容: " + JSON.toJSONString(services));
        return services.toString();
    }
}
