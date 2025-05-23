/**
 * @file DockFX.java
 * @brief Driver demonstrating basic dock layout with prototypes. Maintained in a separate package
 * to ensure the encapsulation of org.dockfx private package members.
 * @section License
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 **/

package ufc.ptt.pttfx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;
import dockfx.DockNode;
import dockfx.DockPane;
import dockfx.DockPosition;

import java.util.Objects;
import java.util.Random;

public class DockFX extends Application {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(DockFX.class);

    public static void main(String[] args) {
        launch(args);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("DockFX");

        // create a dock pane that will manage our dock nodes and handle the layout
        DockPane dockPane = new DockPane();

        // create a default test node for the center of the dock area
        TabPane tabs = new TabPane();
        HTMLEditor htmlEditor = new HTMLEditor();

        String html = """
                        <html>
                        <head>
                            <title></title>
                        </head>
                        <body>
                        <h2>Hello, World!</h2>
                        
                        <p>Welcome to the DockFX demo, the fully featured docking library for JavaFX! This library is
                            completely gratis and open source under the GNU Lesser General Public License version 3. Please see
                            the included LICENSE file for further details. The source code can be downloaded
                            <a href="https://github.com/RobertBColton/DockFX">from GitHub</a> where binary releases are
                            regularly made available.</p>
                        
                        <p>A number of features are supported:</p>
                        
                        <ul>
                            <li>Full documentation</li>
                            <li>Gratis and open source</li>
                            <li>CSS and styling support</li>
                        </ul>
                        </body>
                        </html>
                       """;

            htmlEditor.setHtmlText(html);


        // empty tabs ensure that dock node has its own background color when floating
        tabs.getTabs().addAll(new Tab("Tab 1", htmlEditor), new Tab("Tab 2"), new Tab("Tab 3"));

        TableView<String> tableView = new TableView<>();
        // this is why @SupressWarnings is used above
        // we don't care about the warnings because this is just a demonstration
        // for docks not the table view
        tableView.getColumns().addAll(new TableColumn<String, String>("A"),
                new TableColumn<String, String>("B"), new TableColumn<String, String>("C"));

        // load an image to caption the dock nodes
        Image dockImage = new Image(Objects.requireNonNull(DockFX.class.getResource("/dockfx/docknode.png")).toExternalForm());

        // create and dock some prototype dock nodes to the middle of the dock pane
        // the preferred sizes are used to specify the relative size of the node
        // to the other nodes

        // we can use this to give our central content a larger area where
        // the top and bottom nodes have a preferred width of 300 which means that
        // when a node is docked relative to them such as the left or right dock below
        // they will have 300 / 100 + 300 (400) or 75% of their previous width
        // after both the left and right node's are docked the center docks end up with 50% of the width

        DockNode tabsDock = new DockNode();
        tabsDock.setPrefSize(300, 100);
        tabsDock.setTitle("Tabs Dock");
        tabsDock.setGraphic(new ImageView(new Image(Objects.requireNonNull(DockFX.class.getResource("/dockfx/docknode.png")).toExternalForm())));
        tabsDock.setContents(tabs);
        tabsDock.setDockPosition(DockPosition.TOP);
        tabsDock.setDockPane(dockPane);

        DockNode tableDock = new DockNode();
        tableDock.setContents(tableView);
        // let's disable our table from being undocked
        tableDock.setDockTitleBar(null);
        tableDock.setPrefSize(300, 100);
        tableDock.setDockPosition(DockPosition.BOTTOM);
        tableDock.setDockPane(dockPane);

        final Menu menu1 = new Menu("File");
        final Menu menu2 = new Menu("Options");
        final Menu menu3 = new Menu("Help");

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(menu1, menu2, menu3);

        ToolBar toolBar = new ToolBar(
                new Button("New"),
                new Button("Open"),
                new Button("Save"),
                new Separator(),
                new Button("Clean"),
                new Button("Compile"),
                new Button("Run"),
                new Separator(),
                new Button("Debug"),
                new Button("Profile")
        );

        VBox vbox = new VBox();
        vbox.getChildren().addAll(menuBar, toolBar, dockPane);
        VBox.setVgrow(dockPane, Priority.ALWAYS);

        primaryStage.setScene(new Scene(vbox, 800, 500));
        primaryStage.sizeToScene();

        primaryStage.show();

        // can be created and docked before or after the scene is created
        // and the stage is shown
        DockNode treeDock = new DockNode();
        treeDock.setContents(generateRandomTree());
        treeDock.setPrefSize(100, 100);
        treeDock.setTitle("Tree dock 1");
        treeDock.setGraphic(new ImageView(dockImage));
        treeDock.setDockPosition(DockPosition.LEFT);
        treeDock.setDockPane(dockPane);
        treeDock = new DockNode();
        treeDock.setContents(generateRandomTree());
        treeDock.setTitle("Tree dock 2");
        treeDock.setGraphic(new ImageView(dockImage));
        treeDock.setPrefSize(100, 100);
        treeDock.setDockPosition(DockPosition.RIGHT);
        treeDock.setDockPane(dockPane);

        // test the look and feel with both Caspian and Modena
        Application.setUserAgentStylesheet(Application.STYLESHEET_MODENA);
        // initialize the default styles for the dock pane and undocked nodes using the DockFX
        // library's internal Default.css stylesheet
        // unlike other custom control libraries this allows the user to override them globally
        // using the style manager just as they can with internal JavaFX controls
        // this must be called after the primary stage is shown
        // https://bugs.openjdk.java.net/browse/JDK-8132900
        // TODO: after this feel free to apply your own global stylesheet using the StyleManager class
    }

    private TreeView<String> generateRandomTree() {
        // create a demonstration tree view to use as the contents for a dock node
        TreeItem<String> root = new TreeItem<>("Root");
        TreeView<String> treeView = new TreeView<>(root);
        treeView.setShowRoot(false);

        // populate the prototype tree with some random nodes
        Random rand = new Random();
        for (int i = 4 + rand.nextInt(8); i > 0; i--) {
            TreeItem<String> treeItem = new TreeItem<>("Item " + i);
            root.getChildren().add(treeItem);
            for (int j = 2 + rand.nextInt(4); j > 0; j--) {
                TreeItem<String> childItem = new TreeItem<>("Child " + j);
                treeItem.getChildren().add(childItem);
            }
        }

        return treeView;
    }
}