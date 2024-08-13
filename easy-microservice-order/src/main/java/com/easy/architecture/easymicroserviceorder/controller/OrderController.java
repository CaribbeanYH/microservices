package com.easy.architecture.easymicroserviceorder.controller;

import com.easy.architecture.api.dto.OrderDTO;
import com.easy.architecture.easymicroserviceorder.service.IOrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yanghai10
 * @ClassName
 * @Description
 * @date 2024/8/12 17:47
 */
@RestController
public class OrderController {

    @Resource
    private IOrderService iOrderService;

    @GetMapping("/queryOrderInfos")
    public List<OrderDTO> queryOrderInfos(String pin) {
        return iOrderService.queryUserOrders(pin);
    }
}
