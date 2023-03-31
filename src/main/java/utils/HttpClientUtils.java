package utils;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class HttpClientUtils {
    /*public static CloseableHttpClient createHttpClient(PoolingHttpClientConnectionManager connectionManager) {
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager).build();
        return httpClient;
    }*/

    public static CloseableHttpResponse createHttpResponse(String uri, CloseableHttpClient httpClient) throws IOException, ParseException {
        HttpGet httpGet = new HttpGet(uri);
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        return httpResponse;
    }

    public static Document createDocument(CloseableHttpResponse httpResponse) throws IOException, ParseException {
        HttpEntity entity = httpResponse.getEntity();
        if (entity != null) {
            String s = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            Document document = Jsoup.parse(s);
            return document;
        }
        return null;
    }


}
