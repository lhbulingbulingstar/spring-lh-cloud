package com.lh.cloud.role;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Author: lh
 * Date: 2022/6/12 19:32
 * @author lh
 */
@SpringBootApplication(scanBasePackages = "com.lh.cloud")
@EnableFeignClients(basePackages = "com.lh.cloud")
@MapperScan("com.lh.cloud.role.mapper")
public class RoleApplication {
    public static void main(String[] args) {
        SpringApplication.run(RoleApplication.class);
    }
}

