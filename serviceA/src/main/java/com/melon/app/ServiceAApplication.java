package com.melon.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import java.util.concurrent.TimeUnit;

/**
 * @author muskmelon
 * @since 1.0
 */
@SpringBootApplication
@EnableEurekaClient
public class ServiceAApplication {

    public static void main(String[] args){
        SpringApplication.run(ServiceAApplication.class, args);
    }

}
