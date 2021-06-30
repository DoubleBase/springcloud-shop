import com.alibaba.fastjson.JSON;
import com.melon.hystrix.command.GetProductInfoCommand;
import com.melon.model.ProductInfo;
import com.netflix.hystrix.HystrixCommand;
import org.junit.Test;

/**
 * 短路器测试
 * @author muskmelon
 * @since 1.0
 */

public class CircuitBreakerTests {

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 15; i++) {
            Long productId = (long) (i + 1);
            HystrixCommand<ProductInfo> hystrixCommand = new GetProductInfoCommand(productId);
            ProductInfo productInfo = hystrixCommand.execute();
            System.out.println("第" + (i + 1) + "次请求结果：" + JSON.toJSON(productInfo));
        }
        System.out.println("============================");
        for (int i = 0; i < 25; i++) {
            Long productId = (long) (i + 1);
            HystrixCommand<ProductInfo> hystrixCommand = new GetProductInfoCommand(-1L);
            ProductInfo productInfo = hystrixCommand.execute();
            System.out.println("第" + (i + 1) + "次请求结果：" + JSON.toJSON(productInfo));
        }
        Thread.sleep(5000);
        System.out.println("============================");
        for (int i = 0; i < 10; i++) {
            HystrixCommand<ProductInfo> hystrixCommand = new GetProductInfoCommand(-1L);
            ProductInfo productInfo = hystrixCommand.execute();
            System.out.println("第" + (i + 1) + "次请求结果：" + JSON.toJSON(productInfo));
        }
        System.out.println("尝试等待3秒中");
        Thread.sleep(3000);
        for (int i = 0; i < 20; i++) {
            Long productId = (long) (i + 1);
            HystrixCommand<ProductInfo> hystrixCommand = new GetProductInfoCommand(productId);
            ProductInfo productInfo = hystrixCommand.execute();
            System.out.println("第" + (i + 1) + "次请求结果：" + JSON.toJSON(productInfo));
        }
    }
}
