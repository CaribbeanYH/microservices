package com.easy.architecture.easymicroserviceribbon.loadBalancer;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

/**
 * @author yanghai10
 * @ClassName
 * @Description
 * @date 2024/8/12 20:10
 */
//@Configuration
//public class MyLoadBalancer implements ReactorServiceInstanceLoadBalancer {
//
//    @Override
//    public Mono<Response<ServiceInstance>> choose(Request request) {
//        //选机器，怎么选，自己定义
//        return null;
//    }
//}
