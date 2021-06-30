package com.melon;

import com.melon.filter.HystrixRequestContextServletFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * @author muskmelon
 * @since 1.0
 */
@SpringBootApplication
public class CacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(CacheApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(new HystrixRequestContextServletFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}
