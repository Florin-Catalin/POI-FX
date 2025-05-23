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
        listView.setPrefHeight(200);
        listView.setFixedCellSize(40);

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
        for (int i = 0; i < 20_000; i++) {
            double lat = 51.505 + Math.random() * 0.1 - 0.05;
            double lon = -0.09 + Math.random() * 0.1 - 0.05;
            markers.add(new Marker(lat, lon, "Marker " + (i + 1)));
        }

        // Create the map window and get its controller for communication
        MapWebViewWindow.MapController mapController = MapWebViewWindow.show(markers);

        // Create and show the marker list window
        Stage window1 = createMarkerListWindow(markers, mapController);
        window1.show();

        // Create and show the DockFX window as the third stage
        Stage dockFXStage = new Stage();
        new DockFX().start(dockFXStage);
    }

    public static void main(String[] args) {
        launch();
    }
}