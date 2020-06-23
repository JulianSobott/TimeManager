package WebView.Gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerWebView implements Initializable {


    @FXML
    private AnchorPane anchorPaneWebview;


    @FXML
    private SplitPane mainWebView;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        mainWebView.setId("BoxWebView");
        createContextMenuMainWindow();

    }

    /**
     * Create context Menu
     */

    private void createContextMenuMainWindow() {

        ContextMenu contextMenu = generateContextMenu();

        contextMenu.getItems().get(0).setOnAction( event -> {

            SplitWindow splitWindow = new SplitWindow(mainWebView, SplitWindow.SplitPaneOrientation.horizontal);
            splitWindow.splitIntoTwoSubWindows();

        });

        contextMenu.getItems().get(1).setOnAction( event -> {

            SplitWindow splitWindow = new SplitWindow(mainWebView, SplitWindow.SplitPaneOrientation.vertical);
            splitWindow.splitIntoTwoSubWindows();

        });



        this.mainWebView.setOnMouseClicked(actionEvent -> {

            if (actionEvent.getButton() == MouseButton.SECONDARY)
                contextMenu.show(mainWebView, actionEvent.getScreenX(), actionEvent.getScreenY());
        });


    }


    private ContextMenu generateContextMenu() {


        ContextMenu contextMenu = new ContextMenu();

        MenuItem menuItemSplitHorizontal = new MenuItem("Horizontal teilen");
        MenuItem menuItemSplitVertical = new MenuItem("Vertikal teilen");
        MenuItem menuItemEditURL = new MenuItem("URL bearbeiten");
        MenuItem menuItemDeleteEntry = new MenuItem("Eintrag löschen");

        contextMenu.getItems().addAll(menuItemSplitHorizontal, menuItemSplitVertical, menuItemEditURL, menuItemDeleteEntry);
        return contextMenu;
    }

}
