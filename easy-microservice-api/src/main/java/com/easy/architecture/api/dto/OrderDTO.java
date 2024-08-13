package com.easy.architecture.api.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yanghai10
 * @ClassName
 * @Description
 * @date 2024/8/12 17:48
 */
@Data
public class OrderDTO implements Serializable {

    private static final long serialVersionUID = -6823173062560244202L;

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 用户PIN
     */
    private String pin;

    /**
     * 订单关联的SKU的名字
     */
    private String skuName;

    /**
     * 订单关联的SKU的数量
     */
    private Integer skuNum;

    /**
     * 服务提供这的信息
     */
    private String provider;
}
