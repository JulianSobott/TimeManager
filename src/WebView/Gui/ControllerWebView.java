package WebView.Gui;

import Calendar.Gui.Settings.ControllerCalendarSettings;
import entryPoint.SceneLoader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerWebView implements Initializable {


    @FXML
    private AnchorPane anchorPaneWebview;

    @FXML
    private VBox VBoxWebViews;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    createContextMenu();

    }





    private HBox createNreHBox(){

        HBox hBox = new HBox();
        return  hBox;
    }


    private VBox createNewVBox(){

        VBox vBox = new VBox();
        return  vBox;
    }


    /**
     *  Create context Menu
     */


    private void createContextMenu(){


        ContextMenu contextMenuCalendar = new ContextMenu();
        MenuItem menuItemSettings = new MenuItem("Einstellungen");
        generateEventSettings(menuItemSettings);
        MenuItem menuItemSave = new MenuItem("Speichern");
        MenuItem menuItemDelete = new MenuItem("Löschen");
        MenuItem menuItemImport = new MenuItem("Importieren");
        MenuItem menuItemExport = new MenuItem("Exportieren");

        contextMenuCalendar.getItems().addAll(menuItemSettings, menuItemSave, menuItemDelete, menuItemImport, menuItemExport);
        anchorPaneWebview.setOnMouseClicked(event ->
                contextMenuCalendar.show(anchorPaneWebview, Side.BOTTOM, anchorPaneWebview.getWidth()/2, anchorPaneWebview.getHeight()/1.75));

        //breite / höhe
    }

    private void generateEventSettings(MenuItem menuItemSettings) {

        menuItemSettings.setOnAction(actionEvent -> {




        });

    }

}
