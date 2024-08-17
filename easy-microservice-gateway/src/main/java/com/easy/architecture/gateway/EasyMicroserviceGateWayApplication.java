package com.easy.architecture.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
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


    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder routeLocatorBuilder) {

        return routeLocatorBuilder.routes()
                .route("search", r -> r.path("/api/search").uri("https://bing.com"))
                .route("easy-microservice-order", r -> r.path("/queryOrderInfos").uri("lb://easy-microservice-order"))
                .build();
    }


    /*
    1:Gateway的客户端回向SpringCloud Gateway发起请求，请求首先会被HttpWebHandlerAdapter进行提取组装成网关的上下文，然后网关的上下文会传递DispatcherHandler。
    2:DispatcherHandler是所有请求的分发处理器，DispatcherHandler主要负责分发请求对应的处理器，比如将请求分发到对应 RoutePredicateHandlerMapping(路由断言处理器映射器）。
    3:路由断言处理映射器主要用于路由的查找，以及找到路由后返回对应的FilteringWebHandler。
    4:FilteringWebHandler主要负责组装Filter链表并调用Filter执行一系列Filter处理，然后把请求转到后端对应的代理服务处理，处理完毕后，将Response返回到Gateway客户端。 在Filter链中，通过虚线分割Filter的原因是，过滤器可以在转发请求之前处理或者接收到被代理服务的返回结果之后处理。所有的Pre类型的Filter执行完毕之后，才会转发请求到被代理的服务处理。被代理的服务把所有请求完毕之后，才会执行Post类型的过滤器。
     */
}
