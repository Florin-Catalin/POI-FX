package ufc.ptt.pttfx;

import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class MapWebViewWindow {
    public static void show() {
        WebView webView = new WebView();
        String url = MapWebViewWindow.class.getResource("/ufc/ptt/pttfx/map.html").toExternalForm();
        webView.getEngine().load(url);

        Stage stage = new Stage();
        stage.setTitle("Interactive Map (WebView)");
        stage.setScene(new Scene(webView, 800, 600));
        stage.show();
    }
}