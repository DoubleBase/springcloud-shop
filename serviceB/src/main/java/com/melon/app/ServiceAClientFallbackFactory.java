package com.melon.app;

import com.melon.api.User;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author muskmelon
 * @since 1.0
 */
@Component
public class ServiceAClientFallbackFactory implements FallbackFactory<ServiceAClient> {

    ServiceAClient fallback = new ServiceAClient() {
        @Override
        public String sayHello(Long id, String name, Integer age) {
            return "sayHello 降级";
        }

        @Override
        public String createUser(User user) {
            return null;
        }

        @Override
        public String updateUser(Long id, User user) {
            return null;
        }

        @Override
        public String deleteUser(Long id) {
            return null;
        }

        @Override
        public User getById(Long id) {
            return null;
        }
    };

    @Override
    public ServiceAClient create(Throwable throwable) {
        return fallback;
    }
}
