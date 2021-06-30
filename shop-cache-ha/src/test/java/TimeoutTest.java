import com.alibaba.fastjson.JSON;
import com.melon.hystrix.command.GetProductInfoCommand;
import com.melon.model.ProductInfo;
import com.melon.util.HttpClientUtils;
import com.netflix.hystrix.HystrixCommand;

/**
 * @author muskmelon
 * @since 1.0
 */
public class TimeoutTest {

    public static void main(String[] args) {
        HystrixCommand<ProductInfo> hystrixCommand = new GetProductInfoCommand(-3L);
        ProductInfo productInfo = hystrixCommand.execute();
        System.out.println("请求结果：" + JSON.toJSON(productInfo));
    }
}
