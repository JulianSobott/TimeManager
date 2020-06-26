package entryPoint.general_settings;

import GuiElements.TabSettings;
import GuiElements.TabWindow;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;

import java.net.URL;
import java.util.ResourceBundle;

public class GeneralSettingsController implements Initializable {

    @FXML private TabSettings tabSettings;
    @FXML private CheckBox cbResizeContent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TabWindow tabWindow = tabSettings.getTabWindow();

    }
}
