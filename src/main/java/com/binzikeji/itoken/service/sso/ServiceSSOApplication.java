package com.binzikeji.itoken.service.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Description
 * @Author Bin
 * @Date 2019/4/16 15:08
 **/
@SpringBootApplication(scanBasePackages = "com.binzikeji.itoken")
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan(value = "com.binzikeji.itoken.service.sso.mapper")
public class ServiceSSOApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceSSOApplication.class, args);
    }
}
