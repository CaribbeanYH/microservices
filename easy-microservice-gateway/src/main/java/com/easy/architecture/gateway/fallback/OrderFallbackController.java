package com.easy.architecture.gateway.fallback;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanghai10
 * @ClassName
 * @Description
 * @date 2024/8/17 13:19
 */
@RestController
public class OrderFallbackController {

    @GetMapping("/orderFallback")
    public Object orderFallback() {
        return "订单服务异常了，开始熔断";
    }
}
