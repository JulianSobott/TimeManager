package KanBan.Gui;

import GuiElements.CustomWidget;
import javafx.fxml.FXML;

import java.awt.*;

public class Note extends CustomWidget {

    @FXML
    private Label lblTitle;
    @FXML
    private Label lblDescription;

    public Note() {
        super("/KanBan/Gui/note.fxml");

    }
}
