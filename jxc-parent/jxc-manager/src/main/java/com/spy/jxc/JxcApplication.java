package com.spy.jxc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: spy
 * @Date: 2020/12/27 23:32
 */
@SpringBootApplication(scanBasePackages = "com.spy.jxc")
public class JxcApplication {
    public static void main(String[] args) {
        SpringApplication.run(JxcApplication.class,args);
    }
}
