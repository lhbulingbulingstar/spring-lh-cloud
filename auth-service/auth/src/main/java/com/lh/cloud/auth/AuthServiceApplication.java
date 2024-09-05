package com.lh.cloud.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;


/**
 * 权限服务启动类
 *
 * @author lh
 */
@SpringBootApplication(scanBasePackages = "com.lh.cloud",exclude = {DataSourceAutoConfiguration.class})
@EnableFeignClients(basePackages = "com.lh.cloud")
public class AuthServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class);
    }
}
