package com.easy.architecture.easymicroservicefegin.controller;

import com.alibaba.fastjson.JSON;
import com.easy.architecture.api.client.OrderClient;
import com.easy.architecture.api.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yanghai10
 * @ClassName
 * @Description
 * @date 2024/8/12 20:13
 */
@Slf4j
@RestController
public class FeignOrderController {

    private final OrderClient orderClient;

    public FeignOrderController(OrderClient orderClient) {
        this.orderClient = orderClient;
    }

    @GetMapping(value = "/feign/queryOrderInfos")
    public List<OrderDTO> queryOrderInfos(String pin) {
        List<OrderDTO> orderDTOS = orderClient.queryOrderInfos(pin);
        log.info("feign result is {}", JSON.toJSONString(orderDTOS));
        return orderDTOS;
    }
}
