// filepath: /home/florin/PTTFx/src/main/java/ufc/ptt/pttfx/MapWebViewWindow.java
package ufc.ptt.pttfx;

import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.util.List;

public class MapWebViewWindow {
    public static void show(List<Marker> markers) {
        WebView webView = new WebView();
        String url = MapWebViewWindow.class.getResource("/ufc/ptt/pttfx/map.html").toExternalForm();
        WebEngine engine = webView.getEngine();
        engine.load(url);

        // Add markers after the document is loaded
        engine.documentProperty().addListener((obs, oldDoc, newDoc) -> {
            if (newDoc != null) {
                for (Marker marker : markers) {
                    String script = String.format(
                            "window.markerClusterGroup.addLayer(L.marker([%f, %f]).bindPopup('%s'));",
                            marker.latitude(), marker.longitude(), marker.popup().replace("'", "\\'")
                    );
                    engine.executeScript(script);
                }
            }
        });

        Stage stage = new Stage();
        stage.setTitle("Interactive Map (WebView)");
        stage.setScene(new Scene(webView, 800, 600));
        stage.show();
    }

    // Convenience method for a single marker
    public static void show(Marker marker) {
        show(List.of(marker));
    }
}