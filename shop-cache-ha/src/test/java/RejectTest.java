import com.alibaba.fastjson.JSON;
import com.melon.hystrix.command.GetProductInfoCommand;
import com.melon.model.ProductInfo;
import com.melon.util.HttpClientUtils;
import com.netflix.hystrix.HystrixCommand;

/**
 * @author muskmelon
 * @since 1.0
 */
public class RejectTest {

    public static void main(String[] args) {
        for (int i = 0; i < 25; i++) {
            new TestThread(i + 1).start();
        }
    }

    private static class TestThread extends Thread {

        private int index;

        public TestThread(int index) {
            this.index = index;
        }

        @Override
        public void run() {
            String url = "http://127.0.0.1:8081/getProductInfo?productId=-2";
            String response = HttpClientUtils.sendGetRequest(url);
            System.out.println("RejectTest 第" + (index + 1) + "次请求结果：" + response);
        }
    }
}
