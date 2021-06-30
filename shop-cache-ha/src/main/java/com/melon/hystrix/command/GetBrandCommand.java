package com.melon.hystrix.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixThreadPoolProperties;

/**
 * @author muskmelon
 * @since 1.0
 */
public class GetBrandCommand extends HystrixCommand<String> {

    private String name;

    public GetBrandCommand(String name){
        super(Setter.withGroupKey(
                HystrixCommandGroupKey.Factory.asKey("GetBrandGroup"))
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                        .withCoreSize(10)
                        .withQueueSizeRejectionThreshold(5)));
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        throw new Exception();
    }

    @Override
    protected String getFallback() {
        System.out.println("降级处理，从本地缓存获取数据");
        return "hua wei";
    }
}
