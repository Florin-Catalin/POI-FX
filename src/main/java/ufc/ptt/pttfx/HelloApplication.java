package ufc.ptt.pttfx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    private Stage createWindow(String title, String buttonText, String helloText, boolean addMapButton) {
        Stage stage = new Stage();
        Label label = new Label();
        Button button = new Button(buttonText);
        button.setOnAction(e -> label.setText(helloText));
        VBox vbox = new VBox(10, button, label);

        if (addMapButton) {
            Button mapButton = new Button("Show Map");
            mapButton.setOnAction(e -> MapWebViewWindow.show());
            vbox.getChildren().add(mapButton);
        }

        Scene scene = new Scene(vbox, 300, 200);
        stage.setTitle(title);
        stage.setScene(scene);
        return stage;
    }

    @Override
    public void start(Stage primaryStage) {
        Stage window1 = createWindow("Window 1", "Say Hello from Window 1", "Hello from Window 1!", true);
        Stage window2 = createWindow("Window 2", "Say Hello from Window 2", "Hello from Window 2!", false);

        window1.show();
        window2.show();
    }

    public static void main(String[] args) {
        launch();
    }
}