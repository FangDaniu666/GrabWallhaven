package downloader;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.TimeUnit;

public class SingleDownload {
    public static void main(String[] args) throws Exception {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(10);
        connectionManager.setDefaultMaxPerRoute(10);
        SingleDownload download = new SingleDownload();
        //单个下载
        String url = "https://w.wallhaven.cc/full/d6/wallhaven-d6poom.png";
        if (url != "") {
            download.doGet(connectionManager, url, 1);
        }
        //批量下载
        String[] urls = new String[]{
                "https://w.wallhaven.cc/full/jx/wallhaven-jx357q.jpg",
                "https://w.wallhaven.cc/full/o5/wallhaven-o53x8m.jpg",
                "https://w.wallhaven.cc/full/6d/wallhaven-6dok66.jpg",
                "https://w.wallhaven.cc/full/gp/wallhaven-gp78gl.jpg",
        };
        if (urls != null) {
            int id = 1;
            for (String u : urls) {
                if (u != "") {
                    download.doGet(connectionManager, u, id);
                    id++;
                }
            }
        }
    }

    public void doGet(PoolingHttpClientConnectionManager connectionManager, String url, int id) throws Exception {
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager).build();
        HttpGet httpGet = new HttpGet(url);

        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        HttpEntity entity = httpResponse.getEntity();
        if (entity != null) {

            File file = new File("src\\main\\resources\\img\\" + id + ".jpg");/*+ ".jpg"*/
            while (file.exists()){
                id++;
                file = new File("src\\main\\resources\\img\\" + id + ".jpg");
            }
            if (!file.exists()) {
                byte[] bytes = EntityUtils.toByteArray(entity);
                if (bytes.length >= 10240) {
                    FileOutputStream outputStream = new FileOutputStream(file);
                    outputStream.write(bytes);
                    System.out.println(id + ".jpg下载完毕！");
                    TimeUnit.SECONDS.sleep(1);
                }
            }

        }
        httpResponse.close();
        EntityUtils.consume(entity);
//        httpClient.close();不需要关闭
    }
}
