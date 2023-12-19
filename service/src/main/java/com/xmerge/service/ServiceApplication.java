package com.xmerge.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Xmerge
 */
@SpringBootApplication
@ComponentScan({"com.xmerge.service", "com.xmerge.cache", "com.xmerge.web"})
@MapperScan(basePackages = "com.xmerge.service.dao.mapper")
public class ServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }

}
