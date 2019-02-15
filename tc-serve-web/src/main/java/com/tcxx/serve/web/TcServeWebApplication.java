package com.tcxx.serve.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication(scanBasePackages = {"com.tcxx.serve"})
@MapperScan({"com.tcxx.serve.service.mapper","com.tcxx.serve.uid.mapper"})
public class TcServeWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(TcServeWebApplication.class, args);
    }

}

