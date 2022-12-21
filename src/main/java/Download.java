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

public class Download {
    /*public static void main(String[] args) throws Exception {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(10);
        connectionManager.setDefaultMaxPerRoute(10);
        Download download = new Download();
        download.
        doGet(connectionManager,
                "https://wallhaven.cc/images/user/avatar/32/1840_ecb33cdbee159e6cce46ae2eca1dd08fd63be3a7306fcc4be7081538e87bc740.jpg"
        ,1,"10",1);
    }*/
    public void doGet(PoolingHttpClientConnectionManager connectionManager,String url,int pageid,String id,int homepage) throws Exception {
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager).build();
        HttpGet httpGet = new HttpGet(url);

        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        HttpEntity entity = httpResponse.getEntity();
        if(entity!=null){

            File dir = new File("src\\main\\resources\\img"+homepage);
            if(!dir.exists()){
                dir.mkdirs();
            }
            File file = new File("src\\main\\resources\\img"+homepage+"\\"+pageid +"-"+ id );/*+ ".jpg"*/

            if(!file.exists()) {
                byte[] bytes = EntityUtils.toByteArray(entity);
                if (bytes.length >= 10240) {
                    FileOutputStream outputStream = new FileOutputStream(file);
                    outputStream.write(bytes);
                    System.out.println(pageid + "-" + id + ".jpg下载完毕！");
                    TimeUnit.SECONDS.sleep(1);
                }
            }

        }
        httpResponse.close();
        EntityUtils.consume(entity);
//        httpClient.close();不需要关闭
    }
}
