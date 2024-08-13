package com.easy.architecture.easymicroserviceribbon.controller;

import com.alibaba.fastjson.JSON;
import com.easy.architecture.api.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yanghai10
 * @ClassName
 * @Description
 * @date 2024/8/12 17:39
 */
@Slf4j
@RestController
public class RibbonController {

    //服务调用地址  http://服务的名字/方法的URL
    public static final String ORDER_SERVICE_URL = "http://easy-microservice-order";

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/ribbonQueryOrderInfos")
    public OrderDTO queryOrderInfos(String pin) {
        String interfaceUrl = ORDER_SERVICE_URL + "/queryOrderInfos?pin=" + pin;
        Object result = restTemplate.getForObject(interfaceUrl, Object.class);
        log.info("ribbon result is {}", JSON.toJSONString(result));
        List<OrderDTO> orderDTOS = JSON.parseArray(JSON.toJSONString(result), OrderDTO.class);
        if (CollectionUtils.isEmpty(orderDTOS)) {
            return null;
        } else {
            return orderDTOS.get(0);
        }
    }
}
