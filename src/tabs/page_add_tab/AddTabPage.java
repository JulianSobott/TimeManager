package tabs.page_add_tab;


import GuiElements.CustomWidget;
import entryPoint.Main;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import tabs.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


/**
 * Page that shows all available Tabs
 */
public class AddTabPage implements Initializable {

    @FXML
    private Pane root;

    @FXML
    private Pane mainContainer;
    private SingleChildLayout container = new SingleChildLayout();
    private Pane paneError = new VBox();
    private Pane paneProgress = new VBox();
    private Pane paneTabs = new HBox();
    private Pane paneTabsList = new VBox();
    private SingleChildLayout paneTabInfo = new SingleChildLayout();

    public AddTabPage() {
        ProgressIndicator progress = new ProgressIndicator();
        paneProgress.getChildren().add(progress);
        paneTabs.getChildren().addAll(paneTabsList, paneTabInfo);
        // responsive design
        // from parent
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainContainer.getChildren().add(container);

        Sizing.setSize(mainContainer, root, 1, 1);
        Sizing.setSize(container, mainContainer, 1, 1);
        Sizing.setSize(paneError, container, 1, 1);
        Sizing.setSize(paneTabs, container, 1, 1);
        Sizing.setSize(paneProgress, container, 1, 1);
        Sizing.setSize(paneTabInfo, paneTabs, 0.5, 1);
        Sizing.setSize(paneTabsList, paneTabs, 0.5, 1);
        loadAvailableTabs();
    }

    private void loadAvailableTabs() {

        Task<List<TabData>> task = new Task<>() {
            @Override
            protected List<TabData> call() throws Exception {
                return Tabs.getAvailableTabs();
            }
        };
        task.setOnRunning(v -> {
            container.set(paneProgress);
        });
        task.setOnCancelled(value -> {
            showNoConnection();
        });
        task.setOnFailed(v -> {
            showNoConnection();
        });
        task.setOnSucceeded(v -> {
            List<TabData> availableTabs = task.getValue();
            if (availableTabs.size() == 0) {
                // Show no tabs available
                Label lblNoTabs = new Label("No Plugins available");
                Hyperlink link = new Hyperlink("Create new plugin");
                link.setOnAction(e -> {
                    // TODO: Correct link
                    Main.application.getHostServices().showDocument("http://127.0.0.1:8080");
                });
                clearPane(paneError);
                paneError.getChildren().addAll(lblNoTabs, link);
                container.set(paneError);
            } else {
                // Show all tabs
                clearPane(paneTabsList);
                for(TabData tab : availableTabs) {
                    if(!InstalledTabs.get().isInstalled(tab.name)) {
                        paneTabsList.getChildren().add(new TabInfo(tab, paneTabInfo, this::loadAvailableTabs));
                    }
                }
                container.set(paneTabs);
            }
        });
        new Thread(task).start();


    }

    private void showNoConnection() {
        clearPane(paneError);
        Label lblError = new Label("No connection to the server. Try again later.");
        Button btnTryAgain = new Button("Try again");

        btnTryAgain.setOnMouseClicked(mouseEvent -> {
            loadAvailableTabs();
        });
        paneError.getChildren().addAll(lblError, btnTryAgain);
        container.set(paneError);
    }

    private void clearPane(Pane pane) {
        pane.getChildren().remove(0, pane.getChildren().size());
    }

    public static interface ReloadList {
        void reloadList();
    }

}
