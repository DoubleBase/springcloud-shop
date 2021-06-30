package com.melon.hystrix.command;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.melon.model.ProductInfo;
import com.melon.util.HttpClientUtils;
import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;

import java.util.Collection;
import java.util.List;

/**
 * @author muskmelon
 * @since 1.0
 */
public class GetProductInfosCollapser extends HystrixCollapser<List<ProductInfo>, ProductInfo, Long> {

    private Long productId;

    public GetProductInfosCollapser(Long productId) {
        this.productId = productId;
    }

    @Override
    public Long getRequestArgument() {
        return productId;
    }

    @Override
    protected HystrixCommand<List<ProductInfo>> createCommand(
            Collection<CollapsedRequest<ProductInfo, Long>> collapsedRequests) {
        StringBuilder stringBuilder = new StringBuilder();
        for (CollapsedRequest<ProductInfo, Long> request : collapsedRequests) {
            stringBuilder.append(request.getArgument()).append(",");
        }
        String params = stringBuilder.toString();
        params = params.substring(0, params.length() - 1);
        System.out.println("createCommand执行，params = " + params);
        return new BatchCommand(collapsedRequests);
    }

    @Override
    protected void mapResponseToRequests(List<ProductInfo> batchResponse, Collection<CollapsedRequest<ProductInfo, Long>> collapsedRequests) {
        int count = 0;
        for (CollapsedRequest<ProductInfo, Long> request : collapsedRequests) {
            request.setResponse(batchResponse.get(count++));
        }
    }

    @Override
    protected String getCacheKey() {
        return "product_info_" + productId;
    }

    private static final class BatchCommand extends HystrixCommand<List<ProductInfo>> {

        private final Collection<CollapsedRequest<ProductInfo, Long>> requests;

        public BatchCommand(Collection<CollapsedRequest<ProductInfo, Long>> requests) {
            super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("productInfoService"))
                    .andCommandKey(HystrixCommandKey.Factory.asKey("BatchCommand"))
            );
            this.requests = requests;
        }

        @Override
        protected List<ProductInfo> run() throws Exception {
            StringBuilder paramBuilder = new StringBuilder();
            for (CollapsedRequest<ProductInfo, Long> request : requests) {
                paramBuilder.append(request.getArgument()).append(",");
            }
            String params = paramBuilder.toString();
            params = params.substring(0, params.length() - 1);

            String url = "http://localhost:8088/getProductList?productIds=" + params;
            String response = HttpClientUtils.sendGetRequest(url);

            List<ProductInfo> productInfos = JSONArray.parseArray(response, ProductInfo.class);
            for (ProductInfo productInfo : productInfos) {
                System.out.println("BatchCommand内部，productInfo=" + JSON.toJSONString(productInfo));
            }
            return productInfos;
        }

    }
}
