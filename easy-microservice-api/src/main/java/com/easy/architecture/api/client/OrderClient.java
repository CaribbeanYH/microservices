package com.easy.architecture.api.client;

import com.easy.architecture.api.dto.OrderDTO;
import com.easy.architecture.api.fallback.OrderClientFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author yanghai10
 * @ClassName
 * @Description
 * @date 2024/8/12 20:29
 * <p>
 * name:               指定Feign Client的名称，如果项目使用了 Ribbon，name属性会作为微服务的名称，用于服务发现。
 * serviceId:          用serviceId做服务发现已经被废弃，所以不推荐使用该配置。
 * value:              指定Feign Client的serviceId，如果项目使用了 Ribbon，将使用serviceId用于服务发现,但上面可以看到serviceId做服务发现已经被废弃，所以也不推荐使用该配置。
 * qualifier:          为Feign Client 新增注解@Qualifier
 * url:                请求地址的绝对URL，或者解析的主机名
 * decode404:          调用该feign client发生了常见的404错误时，是否调用decoder进行解码异常信息返回,否则抛出FeignException。
 * fallback:           定义容错的处理类，当调用远程接口失败或超时时，会调用对应接口的容错逻辑,fallback 指定的类必须实现@FeignClient标记的接口。实现的法方法即对应接口的容错处理逻辑。
 * fallbackFactory:    工厂类，用于生成fallback 类示例，通过这个属性我们可以实现每个接口通用的容错逻辑，减少重复的代码。
 * path:               定义当前FeignClient的所有方法映射加统一前缀。
 * primary:            是否将此Feign代理标记为一个Primary Bean，默认为ture
 */
//@FeignClient(name = "easy-microservice-order")
@FeignClient(name = "easy-microservice-order", fallback = OrderClientFallBack.class)
public interface OrderClient {

    /**
     * 查询订单信息
     *
     * @param pin 用户PIN
     * @return 订单DTO对象
     */
    @GetMapping("/queryOrderInfos")
    List<OrderDTO> queryOrderInfos(@RequestParam(value = "pin") String pin);
}
