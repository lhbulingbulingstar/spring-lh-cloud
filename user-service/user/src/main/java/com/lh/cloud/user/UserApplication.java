package com.lh.cloud.user;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


/**
 * Date: 2022/6/12 19:32
 * @author lh
 */
@SpringBootApplication(scanBasePackages = {"com.lh.cloud"})
@EnableFeignClients(basePackages = "com.lh.cloud")
@MapperScan("com.lh.cloud.user.mapper")
@Slf4j
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class);
        System.out.println("=========================================");
        System.out.println("=--------------user启动完成--------------=");
        System.out.println("=========================================");
    }
}
