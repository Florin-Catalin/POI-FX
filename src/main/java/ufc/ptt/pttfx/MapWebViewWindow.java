package ufc.ptt.pttfx;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.scene.layout.Priority;

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
                            "window.markerClusterGroup.addLayer(L.marker([%f, %f], {icon: window.createRectIcon('%s'), title: '%s'}).bindPopup('%s'));",
                            marker.latitude(), marker.longitude(),
                            marker.popup().replace("'", "\\'"),
                            marker.popup().replace("'", "\\'"),
                            marker.popup().replace("'", "\\'")
                    );
                    engine.executeScript(script);
                }
            }
        });

        // JavaFX search bar
        TextField searchField = new TextField();
        searchField.setPromptText("Search marker...");
        searchField.setPrefWidth(30);

        Button searchButton = new Button("Search");
        searchButton.getStyleClass().addAll("btn","btn-success");
        System.out.println(searchButton.getStyleClass());
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

        HBox searchBox = new HBox(10, searchField, searchButton);
        searchBox.getStyleClass().add("btn-toolbar"); // Optional: BootstrapFX toolbar style
        // Add margin and padding for spacing
        searchBox.setStyle("-fx-padding: 16 16 16 16; -fx-background-color: transparent;");
        HBox.setHgrow(searchField, Priority.ALWAYS);

        BorderPane root = new BorderPane();
        root.setTop(searchBox);
        root.setCenter(webView);
        root.setStyle("-fx-padding: 0 0 16 0;"); // space below search bar

        Scene scene = new Scene(root, 800, 650);
        scene.getStylesheets().add(MapWebViewWindow.class.getResource("/bootstrapfx.css").toExternalForm());

        Stage stage = new Stage();
        stage.setTitle("Interactive Map (WebView)");
        stage.setScene(scene);
        stage.show();

        return new MapController(engine);
    }

    public static void show(Marker marker) {
        show(List.of(marker));
    }
}






