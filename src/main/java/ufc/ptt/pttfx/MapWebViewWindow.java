package ufc.ptt.pttfx;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.util.List;

public class MapWebViewWindow {

    public static class MapController {
        private final WebEngine engine;

        public MapController(WebEngine engine) {
            this.engine = engine;
        }

        public void centerOnMarker(Marker marker) {
            String script = String.format(
                    """
                    (function() {
                        var found = null;
                        if(window.markerClusterGroup && window.markerClusterGroup.getLayers) {
                            var layers = window.markerClusterGroup.getLayers();
                            for (var i = 0; i < layers.length; i++) {
                                var m = layers[i];
                                if (m.options && m.options.title && m.options.title === '%s') {
                                    found = m;
                                    break;
                                }
                            }
                            if (found) {
                                window.map.setView(found.getLatLng(), 20);
                                found.openPopup();
                            }
                        }
                    })();
                    """,
                    marker.popup().replace("'", "\\'")
            );
            engine.executeScript(script);
        }
    }

    public static MapController show(List<Marker> markers) {
        WebView webView = new WebView();
        String url = MapWebViewWindow.class.getResource("/ufc/ptt/pttfx/map.html").toExternalForm();
        WebEngine engine = webView.getEngine();
        engine.load(url);

        engine.documentProperty().addListener((obs, oldDoc, newDoc) -> {
            if (newDoc != null) {
                for (Marker marker : markers) {
                    String script = String.format(
                            "window.markerClusterGroup.addLayer(L.marker([%f, %f], {title: '%s'}).bindPopup('%s'));",
                            marker.latitude(), marker.longitude(),
                            marker.popup().replace("'", "\\'"),
                            marker.popup().replace("'", "\\'")
                    );
                    engine.executeScript(script);
                }
            }
        });

        // JavaFX search bar (optional, can be removed if not needed)
        TextField searchField = new TextField();
        searchField.setPromptText("Search marker...");
        Button searchButton = new Button("Search");

        searchButton.setOnAction(e -> {
            String searchText = searchField.getText().replace("'", "\\'");
            String script = """
                (function() {
                    var found = null;
                    if(window.markerClusterGroup && window.markerClusterGroup.getLayers) {
                        var layers = window.markerClusterGroup.getLayers();
                        for (var i = 0; i < layers.length; i++) {
                            var m = layers[i];
                            if (m.options && m.options.title && m.options.title.toLowerCase() === '%s'.toLowerCase()) {
                                found = m;
                                break;
                            }
                        }
                        if (found) {
                            window.map.setView(found.getLatLng(), 20);
                            found.openPopup();
                        } else {
                            alert('Marker not found');
                        }
                    }
                })();
                """.formatted(searchText);
            engine.executeScript(script);
        });

        HBox searchBox = new HBox(5, searchField, searchButton);
        BorderPane root = new BorderPane();
        root.setTop(searchBox);
        root.setCenter(webView);

        Stage stage = new Stage();
        stage.setTitle("Interactive Map (WebView)");
        stage.setScene(new Scene(root, 800, 650));
        stage.show();

        return new MapController(engine);
    }

    public static void show(Marker marker) {
        show(List.of(marker));
    }
}






