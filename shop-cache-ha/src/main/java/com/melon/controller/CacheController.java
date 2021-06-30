package com.melon.controller;

import com.alibaba.fastjson.JSON;
import com.melon.hystrix.command.GetBrandCommand;
import com.melon.hystrix.command.GetProductInfoCommand;
import com.melon.hystrix.command.GetProductInfosCollapser;
import com.melon.hystrix.command.GetProductsObservableCommand;
import com.melon.model.ProductInfo;
import com.melon.util.HttpClientUtils;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixObservableCommand;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import rx.Observable;
import rx.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;


/**
 * 缓存服务的接口
 *
 * @author muskmelon
 */
@Controller
public class CacheController {

    @RequestMapping("/change/product")
    @ResponseBody
    public String changeProduct(Long productId) {
        // 拿到一个商品id
        // 调用商品服务的接口，获取商品id对应的商品的最新数据
        // 用HttpClient去调用商品服务的http接口
        String url = "http://127.0.0.1:8088/getProductInfo?productId=" + productId;
        String response = HttpClientUtils.sendGetRequest(url);
        System.out.println(response);
        return "success";
    }

    @RequestMapping("/getProductInfo")
    @ResponseBody
    public String getProductInfo(Long productId) {
        HystrixCommand<ProductInfo> hystrixCommand = new GetProductInfoCommand(productId);
        ProductInfo productInfo = hystrixCommand.execute();
        System.out.println(JSON.toJSON(productInfo));
        return "success";
    }

    @RequestMapping("/getBrandInfo")
    @ResponseBody
    public String getBrandInfo(String name) {
        GetBrandCommand brandCommand = new GetBrandCommand(name);
        String res = brandCommand.execute();
        return res;
    }

    @RequestMapping("/getProductInfos")
    @ResponseBody
    public String getProductInfos(String productIdStr) {
        String[] productIds = productIdStr.split(",");
        /*HystrixObservableCommand<ProductInfo> hystrixCommand = new GetProductsObservableCommand(productIds);
        Observable<ProductInfo> observable = hystrixCommand.observe();
        List<ProductInfo> list = new ArrayList<>();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        observable.subscribe(new Observer<ProductInfo>() {
            @Override
            public void onCompleted() {
                System.out.println("获取完所有的任务");
                countDownLatch.countDown();
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onNext(ProductInfo productInfo) {
                System.out.println(JSON.toJSON(productInfo));
                list.add(productInfo);
            }
        });
        try {
            countDownLatch.await(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        for (String productId : productIds) {
            HystrixCommand<ProductInfo> hystrixCommand = new GetProductInfoCommand(Long.valueOf(productId));
            ProductInfo productInfo = hystrixCommand.execute();
            System.out.println("调用是否从缓存中来：" + hystrixCommand.isResponseFromCache());
            System.out.println(JSON.toJSON(productInfo));
        }
        return "success";
    }

    @RequestMapping("/getProductList")
    @ResponseBody
    public String getProductList(String productIds) {

        List<Future> futures = new ArrayList<>();

        for (String productId : productIds.split(",")) {
            GetProductInfosCollapser getProductInfosCollapser = new GetProductInfosCollapser(Long.valueOf(productId));
            futures.add(getProductInfosCollapser.queue());
        }

        try {
            for (Future future : futures) {
                System.out.println("CacheController结果：" + future.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "success";
    }

}
