import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.nio.charset.StandardCharsets;

public class GetPageUrl {
    public static void doGet(PoolingHttpClientConnectionManager connectionManager, int start,int end,String url) throws Exception {
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager).build();
        for (int homepage = start; homepage <= end; homepage++) {

            HttpGet httpGet = new HttpGet(url + homepage);
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                String s = EntityUtils.toString(entity, StandardCharsets.UTF_8);
                Document document = Jsoup.parse(s);
                Elements preview = document.getElementsByClass("preview");//根据标签属性
                int pageid = 1;
                for (Element pre : preview) {
                    String page = pre.attr("href");//根据属性查找
                    System.out.println(page);
                    GetUrl getUrl = new GetUrl();
                    getUrl.doGet(connectionManager, page, pageid ,homepage);
                    pageid++;
                }

                httpResponse.close();
//        httpClient.close();不需要关闭
            }
        }
    }
}
