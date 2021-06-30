package com.melon.hystrix.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

/**
 * @author muskmelon
 * @since 1.0
 */
public class SemaphoreCommand extends HystrixCommand<String> {

    protected SemaphoreCommand() {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("semaphoreGroup"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                    .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)));
    }

    @Override
    protected String run() throws Exception {
        return null;
    }
}
