package com.easy.architecture.easymicroserviceorder.service;


import com.easy.architecture.api.dto.OrderDTO;

import java.util.List;

/**
 * @author yanghai10
 * @ClassName
 * @Description
 * @date 2024/8/12 17:47
 */
public interface IOrderService {

    /**
     * 查询用户订单信息
     *
     * @param pin 用户账号
     * @return 返回用户订单列表
     */
    List<OrderDTO> queryUserOrders(String pin);
}
