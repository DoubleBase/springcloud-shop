package com.melon.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * @author muskmelon
 * @since 1.0
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class ServiceBApplication {

    public static void main(String[] args){
        SpringApplication.run(ServiceBApplication.class, args);
    }
}
