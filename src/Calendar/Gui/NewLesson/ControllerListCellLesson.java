package Calendar.Gui.NewLesson;

import Calendar.Logic.Subject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class ControllerListCellLesson extends ListCell<Subject> {

    @FXML
    private HBox hBoxSubjectInformation;

    @FXML
    private Label labelSubject;

    @FXML
    private Pane paneSubjectColor;

    private FXMLLoader mLLoader;


    @Override
    protected void updateItem(Subject subject, boolean empty) {

        super.updateItem(subject, empty);
        if (empty || subject == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/Calendar/Gui/NewLesson/ListCellLesson.fxml"));
                mLLoader.setController(this);
                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String color = subject.getColor().toString();
                String paneStyle = "-fx-background-color:" + color.replace("0x", "#");
                paneSubjectColor.setStyle("-fx-background-color:" + color.replace("0x", "#"));
                paneSubjectColor.setId("paneSubjectColor");
                labelSubject.setText(String.valueOf(subject.getSubjectName()));

            }
            setGraphic(hBoxSubjectInformation);
        }
    }
}
