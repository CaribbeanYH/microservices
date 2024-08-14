package com.easy.architecture.api.fallback;

import com.easy.architecture.api.client.OrderClient;
import com.easy.architecture.api.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;

/**
 * @author yanghai
 * @ClassName
 * @Description
 * @date 2024/8/14 01:03
 */
@Slf4j
@Configuration
public class OrderClientFallBack implements OrderClient {

    @Override
    public List<OrderDTO> queryOrderInfos(String pin) {
        log.info("订单服务不可用，服务熔断了。。。。");
        return Collections.emptyList();
    }
}
