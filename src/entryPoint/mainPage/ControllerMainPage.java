package entryPoint.mainPage;

import GuiElements.TabWindow;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerMainPage implements Initializable {

    @FXML
    TabWindow window;

    @FXML
    Button debugButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        debugButton.setVisible(false);
        debugButton.setOnMousePressed(e -> {
            window.getTabs().add(new Tab("Test", new Label("Hello in a new tab")));
        });
    }
}
