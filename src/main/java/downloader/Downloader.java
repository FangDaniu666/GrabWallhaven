package downloader;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.ResourceBundle;

public class Downloader extends Application {
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TextField single;
    @FXML
    private TextArea multiple;
    @FXML
    private Button downloadbutton;
    @FXML
    private TextField start;
    @FXML
    private TextField end;
    @FXML
    private TextField pageurl;

    public static void main(String[] args) {
        launch(args);
    }

    @FXML
    void initialize() {
        assert single != null : "fx:id=\"single\" was not injected: check your FXML file 'downloader.fxml'.";
        assert multiple != null : "fx:id=\"multiple\" was not injected: check your FXML file 'downloader.fxml'.";
        assert downloadbutton != null : "fx:id=\"downloadbutton\" was not injected: check your FXML file 'downloader.fxml'.";
        assert start != null : "fx:id=\"start\" was not injected: check your FXML file 'downloader.fxml'.";
        assert end != null : "fx:id=\"end\" was not injected: check your FXML file 'downloader.fxml'.";
        assert pageurl != null : "fx:id=\"pageurl\" was not injected: check your FXML file 'downloader.fxml'.";

    }

    @FXML
    void download(ActionEvent event) throws Exception {
        String url = single.getText();
        String pageurls = pageurl.getText();
        // 采用逗号的方式进行分割处理
        String multipleText = multiple.getText();
        String[] urls = multipleText.split("\n");
        //连接
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(10);
        connectionManager.setDefaultMaxPerRoute(10);
        SingleDownload download = new SingleDownload();
        //单个下载
        if (url != "") {
            download.doGet(connectionManager, url, 1);
        }
        //批量下载
        if (urls != null) {
            int id = 1;
            for (String u : urls) {
                if (u != "") {
                    download.doGet(connectionManager, u, id);
                    id++;
                }
            }
        }
        //下载start到end页图片
        if (pageurls != null) {
            Class c = Class.forName("GetPageUrl");
            Method m = c.getDeclaredMethod("doGet", PoolingHttpClientConnectionManager.class, int.class, int.class, String.class);
            m.invoke(c.newInstance(), connectionManager, 21, 21, pageurls);
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        Pane root = FXMLLoader.load(getClass().getResource("/fxml/downloader.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("图片下载");
        stage.show();
    }
}
