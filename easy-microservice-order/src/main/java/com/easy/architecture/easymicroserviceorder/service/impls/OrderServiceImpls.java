package com.easy.architecture.easymicroserviceorder.service.impls;

import com.easy.architecture.api.dto.OrderDTO;
import com.easy.architecture.easymicroserviceorder.config.ServerConfig;
import com.easy.architecture.easymicroserviceorder.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yanghai10
 * @ClassName
 * @Description
 * @date 2024/8/12 17:50
 */
@Slf4j
@Service("iOrderService")
public class OrderServiceImpls implements IOrderService {

    @Resource
    private ServerConfig serverConfig;

    @Resource
    private InetUtils inetUtils;

    @Override
    public List<OrderDTO> queryUserOrders(String pin) {
        try {
            if (StringUtils.equals(pin, "hystrix")) {
                Thread.sleep(2000);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        List<OrderDTO> result = new ArrayList<>();
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setPin(pin);
        orderDTO.setOrderId(114253513412341234L);
        orderDTO.setSkuName("华硕玩家国度" + pin + "i9-1490HX");
        orderDTO.setSkuNum(1);
        result.add(orderDTO);
        String ipAddress = inetUtils.findFirstNonLoopbackHostInfo().getIpAddress();
        Integer port = serverConfig.getPort();
        orderDTO.setProvider(ipAddress + ":" + port);
        log.info(ipAddress + ":" + port + "，请求处理完成！！！！");
        return result;
    }
}
