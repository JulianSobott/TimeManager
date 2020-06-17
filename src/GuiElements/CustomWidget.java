package GuiElements;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class CustomWidget extends Pane {

    protected Pane fxml;

    public CustomWidget(String fxmlFile) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        loader.setRoot(this);
        loader.setController(this);

        try {
            fxml = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
