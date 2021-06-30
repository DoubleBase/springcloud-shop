package com.melon.hystrix.command;

import com.alibaba.fastjson.JSON;
import com.melon.model.ProductInfo;
import com.melon.util.HttpClientUtils;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;

/**
 * @author muskmelon
 * @since 1.0
 */
public class GetProductInfoCommand extends HystrixCommand<ProductInfo> {

    private Long productId;

    public GetProductInfoCommand(Long productId) {
        super(Setter.withGroupKey(
                HystrixCommandGroupKey.Factory.asKey("GetProductInfoGroup"))
                .andThreadPoolPropertiesDefaults(
                        HystrixThreadPoolProperties.Setter()
                        .withCoreSize(10).withMaxQueueSize(12)
                        .withQueueSizeRejectionThreshold(8))
                .andCommandPropertiesDefaults(
                        HystrixCommandProperties.Setter()
                        .withCircuitBreakerRequestVolumeThreshold(30)
                        .withCircuitBreakerErrorThresholdPercentage(40)
                        .withCircuitBreakerSleepWindowInMilliseconds(3000)
                        .withExecutionTimeoutInMilliseconds(500)
                        .withFallbackIsolationSemaphoreMaxConcurrentRequests(30)
                ));
        this.productId = productId;
    }

    @Override
    protected ProductInfo run() throws Exception {
        if (productId == -1L){
            throw new Exception();
        }
        if (productId == -2L){
            Thread.sleep(3000);
        }
        String url = "http://127.0.0.1:8088/getProductInfo?productId=" + productId;
        String response = HttpClientUtils.sendGetRequest(url);
        return JSON.parseObject(response, ProductInfo.class);
    }

    @Override
    protected ProductInfo getFallback() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setName("降级商品");
        return productInfo;
    }

    /*@Override
    protected String getCacheKey() {
        return "product_id_" + productId;
    }*/
}
