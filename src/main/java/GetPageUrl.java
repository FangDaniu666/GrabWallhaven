import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.HttpClientUtils;

public class GetPageUrl {
    public static void doGet(int start, int end, String url) throws Exception {
        //创建连接池
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(10);
        connectionManager.setDefaultMaxPerRoute(10);
        //放出http请求
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager).build();
        for (int homepage = start; homepage <= end; homepage++) {
            CloseableHttpResponse httpResponse = HttpClientUtils.createHttpResponse(url + homepage, httpClient);
            Document document = HttpClientUtils.createDocument(httpResponse);
            Elements preview = document.getElementsByClass("preview");//根据标签属性
            int pageid = 1;
            for (Element pre : preview) {
                String page = pre.attr("href");//根据属性查找
                System.out.println(page);
                GetUrl getUrl = new GetUrl();
                getUrl.doGet(connectionManager, page, pageid, homepage);
                pageid++;
            }
            httpResponse.close();
//        httpClient.close();不需要关闭
        }
    }
}
