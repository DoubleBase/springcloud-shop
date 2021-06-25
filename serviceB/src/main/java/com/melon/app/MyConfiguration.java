package com.melon.app;

import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author muskmelon
 * @since 1.0
 */
@Configuration
public class MyConfiguration {

    @Bean
    public Retryer feignRetryer() {
        return new Retryer.Default();
    }

}
