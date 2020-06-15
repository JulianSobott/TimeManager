package KanBan.Gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class KanbanController implements Initializable {

    @FXML
    private Pane paneUrgentNote;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Note n = new Note();
        paneUrgentNote.getChildren().add(n);
    }
}
