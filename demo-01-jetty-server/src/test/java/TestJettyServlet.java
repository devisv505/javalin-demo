import com.devisv.javalin.demo01.JettyServer;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class TestJettyServlet {

    @Before
    public void before() throws Exception {
        JettyServer.start();
    }

    @Test
    public void test() throws IOException {

        String url = "http://localhost:9000/status";

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet getRequest = new HttpGet(url);

        try {
            assertEquals(client.execute(getRequest)
                               .getStatusLine()
                               .getStatusCode(),
                    200);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @After
    public void after() throws Exception {
        JettyServer.stop();
    }

}
