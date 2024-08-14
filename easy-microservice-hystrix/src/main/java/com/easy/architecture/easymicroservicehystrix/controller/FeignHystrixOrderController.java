package com.easy.architecture.easymicroservicehystrix.controller;

import com.alibaba.fastjson.JSON;
import com.easy.architecture.api.client.OrderClient;
import com.easy.architecture.api.dto.OrderDTO;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * @author yanghai10
 * @ClassName
 * @Description
 * @date 2024/8/12 20:13
 */
@Slf4j
@RestController
public class FeignHystrixOrderController {

    private final OrderClient orderClient;

    public FeignHystrixOrderController(OrderClient orderClient) {
        this.orderClient = orderClient;
    }

//    @HystrixCommand(fallbackMethod = "queryOrderInfosFallback")
    @GetMapping(value = "/hystrix/queryOrderInfos")
    public List<OrderDTO> queryOrderInfos(String pin) {
//        try {
//            if (StringUtils.equals(pin, "hystrix")) {
//                Thread.sleep(2000);
//            }
//        } catch (Exception exception) {
//            exception.printStackTrace();
//        }
        List<OrderDTO> orderDTOS = orderClient.queryOrderInfos(pin);
        log.info("feign result is {}", JSON.toJSONString(orderDTOS));
        return orderDTOS;
    }

    /**
     * 降级方法
     * ！！！！注意：熔断的降级逻辑方法必须跟正常逻辑方法有相同的参数列表和返回值声明。！！！！
     */
    public List<OrderDTO> queryOrderInfosFallback(String pin) {
        log.info("查询用户订单{}订单服务失败了，降级方法被调用了", pin);
        return Collections.emptyList();
    }

    /**
     状态机有3个状态：
     Closed：关闭状态（断路器关闭），所有请求都正常访问。
     Open：打开状态（断路器打开），所有请求都会被降级。Hystrix会对请求情况计数，当一定时间内失败请求百分比达到阈值，则触发熔断，断路器会完全打开。默认失败比例的阈值是50%，请求次数最少不低于20次。
     Half Open：半开状态，不是永久的，断路器打开后会进入休眠时间（默认是5秒）。随后断路器会自动进入半开状态。此时会释放部分请求通过，若这些请求都是健康的，则会关闭断路器，否则继续保持打开，再次进行休眠计时
     */

    /**
     feign和ribbon的配置二选一即可（如果都配置了，会以feign的配置为准），因为这两个配置都相当于是ribbon的配置，不同点在于feign的配置在ribbon的基础上做了扩展，可以支持配置服务级别的超时时间，并且如果feignClient中使用了url，超时时间也可以生效（ribbon当指定了url时是不会执行到的，因为指定了url就不需要走ribbon的负载均衡逻辑）。
     hystrix配置的超时时间理论上应该要比
     feign和ribbon的要大，因为feign和ribbon可以配置失败重试。当然最终的超时时间是以feign（或ribbon）和hystrix中最小时间为准。所以在单独设置某个接口的超时时间时，如果设置的超时时间比feign的要小，则可以生效。如果设置的时间比feign的要大，则会以feign的超时时间为准。
     */
}
