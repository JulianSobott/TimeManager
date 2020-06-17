package WebView.Gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
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
        createMainWebViewBoxContextMenu();

    }


    private HBox createNewHBox(String id, int height) {

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


    private void createMainWebViewBoxContextMenu() {

        ContextMenu contextMenuCalendar = new ContextMenu();

        MenuItem menuItemSplitHorizontal = new MenuItem("Horizontal teilen");
        generateEventSplitHorizontal(menuItemSplitHorizontal);

        MenuItem menuItemSplitVertical = new MenuItem("Vertikal teilen");
        generateEventSplitVertical(menuItemSplitVertical);

        MenuItem menuItemEditURL = new MenuItem("URL bearbeiten");

        MenuItem menuItemDeleteEntry = new MenuItem("Eintrag löschen");

        contextMenuCalendar.getItems().addAll(menuItemSplitHorizontal, menuItemSplitVertical, menuItemEditURL, menuItemDeleteEntry);
        anchorPaneWebview.setOnMouseClicked(event ->
                contextMenuCalendar.show(anchorPaneWebview, Side.BOTTOM, anchorPaneWebview.getWidth() / 2, -anchorPaneWebview.getHeight() / 2));

        //breite -> rechts / höhe -> oben
    }


    private void generateEventSplitHorizontal(MenuItem menuItemSplitHorizontal) {

        menuItemSplitHorizontal.setOnAction(actionEvent -> {

                HBox h1 = createNewHBox("BoxWebViewLight", 300);
                h1.getChildren().add(generateAddURL());


                HBox h2 = createNewHBox("BoxWebViewLight", 300);
                h2.getChildren().add(generateAddURL());


                mainWebViewVBox.getChildren().addAll(h1, h2);

        });

    }


    private HBox generateAddURL(){

        HBox hBox = new HBox();
        hBox.setSpacing(10);
        Button button = new Button("URL anzeigen");
        TextField textField = new TextField();
        textField.setPromptText("http://google.de");

        hBox.getChildren().addAll(textField, button);
        return hBox;
    }


    private void generateEventSplitVertical(MenuItem menuItemSplitVertical) {

        menuItemSplitVertical.setOnAction(actionEvent -> {


        });

    }

}
