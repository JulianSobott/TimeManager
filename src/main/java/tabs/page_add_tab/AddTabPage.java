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
import javafx.scene.layout.*;
import tabs.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


/**
 * Page that shows all available Tabs
 */
public class AddTabPage extends StackPane implements Initializable {

    @FXML
    private Pane root;

    @FXML GridPane containerStandardPlugins;
    @FXML GridPane containerUserPlugins;
    @FXML Pane containerTabInfo;
    @FXML ProgressIndicator progressIndicator;
    @FXML VBox containerError;

    private SingleChildLayout tabInfoPane;

    public AddTabPage() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tabInfoPane = new SingleChildLayout();
        containerTabInfo.getChildren().add(tabInfoPane);

        loadAvailableTabs();

        // Standard tabs
        TabData tabDataWebsite = new TabData("Webseite", new String[]{},
                "Ein Fenster in das Webseiten geladen werden",
                "Ein Fenster in das Webseiten geladen werden. Diese können beliebig angeordnet werden.");
        TabInfo tabInfoWebsite = new TabInfoBuiltin(tabDataWebsite, tabInfoPane);
        containerStandardPlugins.add(tabInfoWebsite, 0, 0);
    }

    private void loadAvailableTabs() {

        Task<List<TabData>> task = new Task<>() {
            @Override
            protected List<TabData> call() throws Exception {
                return Tabs.getAvailableTabs();
            }
        };
        task.setOnRunning(v -> {
            clearPane(containerError);
            showProgress(true);
        });
        task.setOnCancelled(value -> {
            showNoConnection();
        });
        task.setOnFailed(v -> {
            showNoConnection();
        });
        task.setOnSucceeded(v -> {
            showProgress(false);
            List<TabData> availableTabs = task.getValue();
            if (availableTabs.size() == 0) {
                // Show no tabs available
                Label lblNoTabs = new Label("No Plugins available");
                Hyperlink link = new Hyperlink("Create new plugin");
                link.setOnAction(e -> {
                    // TODO: Correct link
                    Main.application.getHostServices().showDocument("http://127.0.0.1:8080");
                });
                clearPane(containerError);
                containerError.getChildren().addAll(lblNoTabs, link);
            } else {
                // Show all tabs
                clearPane(containerUserPlugins);
                int row = 0;
                int col = 0;
                for(TabData tab : availableTabs) {
                    if(!InstalledTabs.get().isInstalled(tab.name)) {
                        containerUserPlugins.add(new TabInfoUser(tab, tabInfoPane, this::loadAvailableTabs), col, row);
                        col++;
                    }
                }
            }
        });
        new Thread(task).start();


    }

    private void showNoConnection() {
        showProgress(false);
        clearPane(containerError);
        Label lblError = new Label("No connection to the server. Try again later.");
        Button btnTryAgain = new Button("Try again");

        btnTryAgain.setOnMouseClicked(mouseEvent -> {
            loadAvailableTabs();
        });
        containerError.getChildren().addAll(lblError, btnTryAgain);
    }

    private void showProgress(boolean show) {
        progressIndicator.setVisible(show);
    }

    private void clearPane(Pane pane) {
        pane.getChildren().remove(0, pane.getChildren().size());
    }

    public static interface ReloadList {
        void reloadList();
    }

}
