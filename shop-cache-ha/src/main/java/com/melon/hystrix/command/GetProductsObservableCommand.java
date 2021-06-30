package com.melon.hystrix.command;

import com.alibaba.fastjson.JSON;
import com.melon.model.ProductInfo;
import com.melon.util.HttpClientUtils;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * @author muskmelon
 * @since 1.0
 */
public class GetProductsObservableCommand extends HystrixObservableCommand<ProductInfo> {

    private String[] productIds;

    public GetProductsObservableCommand(String[] productIds) {
        super(HystrixCommandGroupKey.Factory.asKey("GetProductInfoGroup"));
        this.productIds = productIds;
    }

    @Override
    protected Observable<ProductInfo> construct() {

        return Observable.create(new Observable.OnSubscribe<ProductInfo>() {
            @Override
            public void call(Subscriber<? super ProductInfo> observer) {
                try {
                    for (String productId : productIds) {
                        String url = "http://127.0.0.1:8088/getProductInfo?productId=" + productId;
                        String response = HttpClientUtils.sendGetRequest(url);
                        ProductInfo productInfo = JSON.parseObject(response, ProductInfo.class);
                        observer.onNext(productInfo);
                    }
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io());
    }

}
