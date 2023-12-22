package com.xmerge.userService;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Xmerge
 */
@SpringBootApplication
@ComponentScan("com.xmerge")
@MapperScan(basePackages = "com.xmerge.userService.dao.mapper")
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

}
