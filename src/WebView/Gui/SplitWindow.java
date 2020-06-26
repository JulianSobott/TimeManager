package WebView.Gui;

import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.web.WebView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Idee: Eigene Klasse zu schreiben die entweder Splitpane mit Horizontaler oder Vertikaler
 * orientierung zurück liefert ..... mit den entsprechenden Referenz auf Parent SplitPane
 * um löschen zu ermöglichen
 * <p>
 * In dem neuen Splitpane sollen dann jeweils 2 bereits vorgebaute Boxen existieren.... mit
 * Button und Label für laden entsprechender Webseiten
 */


public class SplitWindow {

    private SplitPane parentNode;
    private boolean buttonState;
    private Orientation splitPaneOrientation;
    private Button btnSplitVertically;
    private Button btnSplitHorizontally;
    private Button btnClose;
    private Button btnEdit;


    public SplitWindow(SplitPane parentNode, Orientation splitPaneOrientation) {
        this.parentNode = parentNode;
        this.splitPaneOrientation = splitPaneOrientation;
    }

    public void createStartView(SplitPane splitPane){
        StackPane stackPane = createStackPane();
        splitPane.getItems().addAll(stackPane);
    }

    /**
     * Method to split a window into two subwindows
     */


    public void splitIntoTwoSubWindows() {

        SplitPane splitPane = createBasicSplitPane();

        this.parentNode.getItems().clear();
        this.parentNode.getItems().add(splitPane);
        this.parentNode = splitPane;
    }


    private SplitPane createBasicSplitPane() {

        SplitPane splitPane = new SplitPane();
        splitPane.setOrientation(splitPaneOrientation);

        StackPane s1 = createStackPane();
        StackPane s2 = createStackPane();

        splitPane.getItems().addAll(s1, s2);

        return splitPane;
    }


    /**
     * ##################################### Generate SplitPane ######################################################
     */



    /**
     * Generates content for empty SplitPane
     */


    private HBox generateAddURL(Pane pane) {

        HBox hBox = new HBox();

        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);

        TextField textField = new TextField();
        textField.setPromptText("http://www.google.de");
        textField.setPrefWidth(300);
        textField.setAlignment(Pos.CENTER);

        Button button = new Button("Webseite anzeigen");
        button.setOnMouseClicked(mouseEvent -> {

            // Create a WebView
            WebView webView = new WebView();
            webView.getEngine().load(textField.getText());
            StackPane stackPane = (StackPane) pane.getParent().getParent();
            HBox urlHBox = (HBox) stackPane.getChildren().get(0);
            urlHBox.getChildren().add(webView);

            // Property Bindings
            webView.prefWidthProperty().bind(pane.widthProperty());

        });

        hBox.getChildren().addAll(textField, button);

        return hBox;
    }


    private HBox createNewHBox(String id) {

        HBox hBox = new HBox();
        hBox.setId(id);
        hBox.setAlignment(Pos.CENTER);
        return hBox;
    }

    /**
     * ######################################### Generate Buttons ################################################
     */

    private StackPane createStackPane(){
        StackPane s = new StackPane();
        s.setPickOnBounds(false);

        HBox h = createNewHBox("BoxWebViewLight");
        //h.getChildren().add(generateAddURL(h));

        VBox buttonVBox = createEditButtons();
        buttonVBox.setVisible(buttonState);

        s.getChildren().addAll(h, buttonVBox);

        return s;
    }

    private VBox createEditButtons(){
        HBox urlTextBox = new HBox();
        urlTextBox.getChildren().add(generateAddURL(urlTextBox));
        urlTextBox.setAlignment(Pos.CENTER);

        HBox buttonBox = new HBox();
        Button close = new Button("close");
        Button horizontal = new Button("horizontal");
        Button vertical = new Button("vertical");
        buttonBox.getChildren().addAll(horizontal, vertical, close);
        buttonBox.setAlignment(Pos.CENTER);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(urlTextBox, buttonBox);
        vBox.setAlignment(Pos.CENTER);

        addButtonFunctions(buttonBox);

        return vBox;
    }

    private void addButtonFunctions(HBox HBoxButtons){

        HBoxButtons.getChildren().get(0).setOnMouseClicked(event -> { //split horizontal button
            this.splitPaneOrientation = Orientation.HORIZONTAL;
            replaceElements((StackPane) HBoxButtons.getParent().getParent());
        });


        HBoxButtons.getChildren().get(1).setOnMouseClicked(event -> { //split vertical button
            this.splitPaneOrientation = Orientation.VERTICAL;
            replaceElements((StackPane) HBoxButtons.getParent().getParent());
        });


//        HBoxButtons.getChildren().get(4).setOnMouseClicked(event -> { //Edit button
//            StackPane stackPane = (StackPane) HBoxButtons.getParent();
//            HBox urlBox = (HBox) stackPane.getChildren().get(0);
//            urlBox.getChildren().clear();
//            urlBox.getChildren().add(generateAddURL(urlBox));
//        });


        HBoxButtons.getChildren().get(2).setOnMouseClicked(event -> { //close button
            StackPane stackPane = (StackPane) HBoxButtons.getParent().getParent();

            if (stackPane.getChildren().size() == 2)
            {
                SplitPane parent = (SplitPane) stackPane.getParent().getParent();
                parent.getItems().remove(stackPane);

            }

            stackPane.getChildren().remove(HBoxButtons);
        });
    }

    /**
     * ######################################### Show Buttons ################################################
     */

    public void showEditButtons(){
        // TODO
    }


    /**
     * Hilfsmethode zum bestimmen des Index
     */

    private void replaceElements(StackPane sPane) {

        SplitPane splitPane = createBasicSplitPane();

        SplitPane parentNode = (SplitPane) sPane.getParent().getParent();

        int index = -1;
        for (int i = 0; i < parentNode.getItems().size(); i++) {

            if (sPane == parentNode.getItems().get(i))
                index = i;

        }

        parentNode.getItems().remove(sPane);
        parentNode.getItems().add(index, splitPane);
    }

    /**
     * ENUM Orientation SplitPane
     */

    private enum Child {

        LEFT_TOP_CHILD, RIGHT_BELOW_CHILD
    }

}
