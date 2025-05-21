package ufc.ptt.pttfx;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.ArrayList;

public class HelloApplication extends Application {

    private Stage createMarkerListWindow(List<Marker> markers, MapWebViewWindow.MapController mapController) {
        Stage stage = new Stage();
        ListView<Marker> listView = new ListView<>(FXCollections.observableList(markers));
        listView.setPrefHeight(200); // Show about 5 items (adjust as needed)
        listView.setFixedCellSize(40); // Adjust for your marker string length

        // When a marker is clicked, center it on the map
        listView.setOnMouseClicked(event -> {
            Marker selected = listView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                mapController.centerOnMarker(selected);
            }
        });

        VBox vbox = new VBox(listView);
        Scene scene = new Scene(vbox, 400, 220);
        stage.setTitle("Markers List");
        stage.setScene(scene);
        return stage;
    }

    @Override
    public void start(Stage primaryStage) {
        // Generate markers
        List<Marker> markers = new ArrayList<>();
        for (int i = 0; i < 30000; i++) {
            double lat = 51.505 + Math.random() * 0.1 - 0.05;
            double lon = -0.09 + Math.random() * 0.1 - 0.05;
            markers.add(new Marker(lat, lon, "Marker " + (i + 1)));
        }

        // Create the map window and get its controller for communication
        MapWebViewWindow.MapController mapController = MapWebViewWindow.show(markers);

        // Create and show the marker list window
        Stage window1 = createMarkerListWindow(markers, mapController);
        window1.show();
    }

    public static void main(String[] args) {
        launch();
    }
}