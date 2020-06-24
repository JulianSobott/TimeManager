package WebView.Gui;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;


/**
 * Idee: Eigene Klasse zu schreiben die entweder Splitpane mit Horizontaler oder Vertikaler
 * orientierung zurück liefert ..... mit den entsprechenden Referenz auf Parent SplitPane
 * um löschen zu ermöglichen
 * <p>
 * In dem neuen Splitpane sollen dann jeweils 2 bereits vorgebaute Boxen existieren.... mit
 * Button und Label für laden entsprechender Webseiten
 */


public class SplitWindow {

    private boolean firstSplit = false;

    private SplitPane parentNode;
    private SplitPaneOrientation splitPaneOrientation;

    private Pane left_Top_Child;
    private Pane right_Below_Child;


    public SplitWindow(SplitPane parentNode, SplitPaneOrientation splitPaneOrientation) {

        this.parentNode = parentNode;
        this.splitPaneOrientation = splitPaneOrientation;
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

        SplitPane splitPane;

        if (splitPaneOrientation == SplitPaneOrientation.horizontal)
            splitPane = generateSplitPaneHorizontal();
        else
            splitPane = generateSplitPaneVertical();

        HBox h1 = createNewHBox("BoxWebViewLight");
        this.left_Top_Child = h1;

        h1.getChildren().add(generateAddURL(h1));
        ContextMenu contextMenuH1 = createContextMenu(h1);
        addFunctionsToContextMenu(contextMenuH1, h1, Child.LEFT_TOP_CHILD);


        HBox h2 = createNewHBox("BoxWebViewLight");
        this.right_Below_Child = h2;

        h2.getChildren().add(generateAddURL(h2));
        ContextMenu contextMenuH2 = createContextMenu(h2);
        addFunctionsToContextMenu(contextMenuH2, h2, Child.RIGHT_BELOW_CHILD);

        splitPane.getItems().addAll(h1, h2);

        return splitPane;
    }


    /**
     * ##################################### Generate SplitPane ######################################################
     */


    private SplitPane generateSplitPaneHorizontal() {

        SplitPane splitPane = new SplitPane();
        splitPane.setOrientation(Orientation.VERTICAL);

        return splitPane;
    }

    private SplitPane generateSplitPaneVertical() {

        SplitPane splitPane = new SplitPane();
        splitPane.setOrientation(Orientation.HORIZONTAL);

        return splitPane;
    }


    /**
     * Generates content for empty SplitPane
     */


    private Pane generateAddURL(Pane pane) {

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
            pane.getChildren().clear();
            pane.getChildren().add(webView);

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
     * ######################################### Generate ContextMenu ################################################
     */


    private ContextMenu createContextMenu(Pane pane) {

        ContextMenu contextMenu = generateContextMenu();
        pane.setOnMouseClicked(event -> {

            if (event.getButton() == MouseButton.SECONDARY)
                contextMenu.show(pane, event.getScreenX(), event.getScreenY());
        });

        return contextMenu;
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


    private void addFunctionsToContextMenu(ContextMenu contextMenu, HBox hBox, Child child) {

        contextMenu.getItems().get(0).setOnAction(event -> {

            this.splitPaneOrientation = SplitPaneOrientation.horizontal;
            replaceElements(hBox);

            //Zeiger noch Zuweißen ...
        });


        contextMenu.getItems().get(1).setOnAction(event -> {

            this.splitPaneOrientation = SplitPaneOrientation.vertical;
           replaceElements(hBox);

            //Zeiger noch Zuweißen
        });

    }


    /**
     *      Hilfsmethode zum bestimmen des Index
     */

    private void replaceElements(HBox hBox){

        SplitPane splitPane = createBasicSplitPane();

        int index = -1;
        for (int i = 0; i < parentNode.getItems().size() ; i++) {

            if (hBox == parentNode.getItems().get(i))
                index = i;

        }

        parentNode.getItems().remove(hBox);
        parentNode.getItems().add(index, splitPane);
    }

    /**
     * ENUM Orientation SplitPane
     */

    public enum SplitPaneOrientation {

        horizontal, vertical
    }

    private enum Child {

        LEFT_TOP_CHILD, RIGHT_BELOW_CHILD
    }

}
