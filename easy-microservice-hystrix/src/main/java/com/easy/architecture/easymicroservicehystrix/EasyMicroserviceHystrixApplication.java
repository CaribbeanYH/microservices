package com.easy.architecture.easymicroservicehystrix;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author yanghai10
 * @ClassName
 * @Description
 * @date 2024/7/28 19:09
 */
@EnableHystrixDashboard
@EnableHystrix
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.easy.architecture"})
@ComponentScan(basePackages = {"com.easy.architecture"})
@SpringBootApplication
public class EasyMicroserviceHystrixApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyMicroserviceHystrixApplication.class, args);
    }

    /**
     * 获取 HystrixMetricsStreamServlet 的 ServletRegistrationBean 对象
     *
     * @return ServletRegistrationBean<HystrixMetricsStreamServlet> 返回 HystrixMetricsStreamServlet 的 ServletRegistrationBean 对象
     */
    @Bean
    public ServletRegistrationBean<HystrixMetricsStreamServlet> getServlet() {
        HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
        ServletRegistrationBean<HystrixMetricsStreamServlet> registrationBean = new ServletRegistrationBean<HystrixMetricsStreamServlet>(streamServlet);
        registrationBean.setLoadOnStartup(1);
        registrationBean.addUrlMappings("/hystrix.stream");
        registrationBean.setName("HystrixMetricsStreamServlet");
        return registrationBean;
    }

}
