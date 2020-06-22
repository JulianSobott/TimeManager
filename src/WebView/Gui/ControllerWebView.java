package WebView.Gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerWebView implements Initializable {


    @FXML
    private AnchorPane anchorPaneWebview;


    @FXML
    private VBox mainWebViewVBox;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        mainWebViewVBox.setId("BoxWebView");
        createContextMenu(mainWebViewVBox, 900.00, 900.00);

    }


    private HBox createNewHBox(String id, Double height) {

        HBox hBox = new HBox();
        hBox.setPrefHeight(height);
        hBox.setId(id);
        hBox.setAlignment(Pos.CENTER);
        return hBox;
    }


    private VBox createNewVBox(String id) {

        VBox vBox = new VBox();
        vBox.setId(id);
        return vBox;
    }



    /**
     * Create context Menu
     */


    private void createContextMenu(Pane pane , Double x, Double y) {

        ContextMenu contextMenu = new ContextMenu();

        MenuItem menuItemSplitHorizontal = new MenuItem("Horizontal teilen");
        generateEventSplitHorizontal(menuItemSplitHorizontal, pane);

        MenuItem menuItemSplitVertical = new MenuItem("Vertikal teilen");
        generateEventSplitVertical(menuItemSplitVertical);

        MenuItem menuItemEditURL = new MenuItem("URL bearbeiten");

        MenuItem menuItemDeleteEntry = new MenuItem("Eintrag löschen");

        contextMenu.getItems().addAll(menuItemSplitHorizontal, menuItemSplitVertical, menuItemEditURL, menuItemDeleteEntry);
        pane.setOnMouseClicked(event ->
                contextMenu.show(pane, Side.BOTTOM, x / 2 , -y / 2));

        //breite -> rechts / höhe -> oben
    }


    private void generateEventSplitHorizontal(MenuItem menuItemSplitHorizontal, Pane pane) {

        menuItemSplitHorizontal.setOnAction(actionEvent -> {

                pane.setOnMouseClicked(null);

                HBox h1 = createNewHBox("BoxWebViewLight", pane.getHeight() * 0.45);
                h1.getChildren().add(generateAddURL());
                createContextMenu(h1, pane.getWidth() /2 , pane.getHeight() /2);

                HBox h2 = createNewHBox("BoxWebViewLight", pane.getHeight() * 0.45);
                h2.getChildren().add(generateAddURL());
                createContextMenu(h2, pane.getWidth() /2 , pane.getHeight() /2);

                mainWebViewVBox.getChildren().addAll(h1, h2);


        });

    }


    private Pane generateAddURL(){

        HBox hBox = new HBox();

        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);

        Button button = new Button("URL anzeigen");
        //Fehlendes Event zum Laden der Webseite

        TextField textField = new TextField();
        textField.setPromptText("http://www.google.de");
        hBox.getChildren().addAll(textField, button);

        return hBox;
    }


    private void generateEventSplitVertical(MenuItem menuItemSplitVertical) {

        menuItemSplitVertical.setOnAction(actionEvent -> {


        });

    }

}
