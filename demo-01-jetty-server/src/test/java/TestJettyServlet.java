import com.devisv.javalin.demo01.JettyServer;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestJettyServlet {

    private final HttpClient client = HttpClientBuilder.create().build();

    @Before
    public void before() throws Exception {
        JettyServer.start();
    }

    @Test
    public void blockServletTest() throws IOException {

        assertEquals(request().getStatusLine()
                              .getStatusCode(),
                200);

        String content =
                IOUtils.toString(
                        request().getEntity().getContent(),
                        StandardCharsets.UTF_8
                );

        assertNotNull(content);

    }

    private HttpResponse request() throws IOException {
        String url = "http://localhost:9000/status";

        HttpGet request = new HttpGet(url);

        return client.execute(request);
    }

    @After
    public void after() throws Exception {
        JettyServer.stop();
    }

}
