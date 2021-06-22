package com.melon.app;

import com.melon.api.ServiceAInterface;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author muskmelon
 * @since 1.0
 */
@FeignClient("serviceA")
public interface ServiceAClient extends ServiceAInterface {
}
