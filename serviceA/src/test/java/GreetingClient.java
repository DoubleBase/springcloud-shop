//import com.netflix.client.ClientFactory;
//import com.netflix.client.http.HttpRequest;
//import com.netflix.client.http.HttpResponse;
//import com.netflix.config.ConfigurationManager;
//import com.netflix.loadbalancer.BaseLoadBalancer;
//import com.netflix.loadbalancer.ILoadBalancer;
//import com.netflix.loadbalancer.Server;
//import com.netflix.niws.client.http.RestClient;

import java.util.ArrayList;
import java.util.List;

/**
 * @author muskmelon
 * @since 1.0
 */
public class GreetingClient {

    public static void main(String[] args) throws Exception {

        /*ILoadBalancer loadBalancer = new BaseLoadBalancer();
        List<Server> serverList = new ArrayList<>();
        serverList.add(new Server("localhost",9001));
        serverList.add(new Server("localhost",9002));

        loadBalancer.addServers(serverList);

        for (int i = 0; i < 10; i++) {
            Server server = loadBalancer.chooseServer(null);
            System.out.println(server.getPort());
        }*/


    }
}
