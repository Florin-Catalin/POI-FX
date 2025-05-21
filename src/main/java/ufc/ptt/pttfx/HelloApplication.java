package ufc.ptt.pttfx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.ArrayList;

public class HelloApplication extends Application {

    private Stage createHelloWindow() {
        Stage stage = new Stage();
        Label label = new Label();
        Button button = new Button("Say Hello from Window 1");
        button.setOnAction(e -> label.setText("Hello from Window 1!"));
        VBox vbox = new VBox(10, button, label);

        Scene scene = new Scene(vbox, 300, 200);
        stage.setTitle("Window 1");
        stage.setScene(scene);
        return stage;
    }

    @Override
    public void start(Stage primaryStage) {
        // Window 1: Say Hello
        Stage window1 = createHelloWindow();

        // Window 2: Map opens directly
        List<Marker> markers = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            double lat = 51.505 + Math.random() * 0.1 - 0.05;
            double lon = -0.09 + Math.random() * 0.1 - 0.05;
            markers.add(new Marker(lat, lon, "Marker " + (i + 1)));
        }
        MapWebViewWindow.show(markers);

        window1.show();
    }

    public static void main(String[] args) {
        launch();
    }
}