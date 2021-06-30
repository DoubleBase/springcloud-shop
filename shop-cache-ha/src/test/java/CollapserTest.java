import com.melon.util.HttpClientUtils;

/**
 * @author muskmelon
 * @since 1.0
 */
public class CollapserTest {

    public static void main(String[] args) {
        HttpClientUtils.sendGetRequest("http://localhost:8081/getProductList?productIds=1,1,2,2,3,4");
    }
}
