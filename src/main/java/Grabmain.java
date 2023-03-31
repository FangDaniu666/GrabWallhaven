import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class Grabmain {
    public static void main(String[] args) throws Exception {
        //下载start到end页图片
        GetPageUrl pageUrl = new GetPageUrl();
        pageUrl.doGet(2,5,"https://wallhaven.cc/search?purity=100&sorting=views&order=desc&page=");
    }

}
