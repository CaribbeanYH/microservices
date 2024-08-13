package com.easy.architecture.easymicroserviceorder.config;

import lombok.Getter;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

/**
 * @author yanghai10
 * @ClassName
 * @Description
 * @date 2024/8/12 19:41
 */
@Getter
@Configuration
public class ServerConfig implements ApplicationListener<WebServerInitializedEvent> {

    private Integer port;

    @Override
    public void onApplicationEvent(WebServerInitializedEvent webServerInitializedEvent) {
        this.port = webServerInitializedEvent.getWebServer().getPort();
    }
}