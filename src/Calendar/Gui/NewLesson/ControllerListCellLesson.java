package Calendar.Gui.NewLesson;

import Calendar.Logic.Subject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class ControllerListCellLesson extends ListCell<Subject> {

    @FXML
    private Label labelProf;

    @FXML
    private Label labelSubject;

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

                labelProf.setText(String.valueOf(subject.getProfessor()));
                labelSubject.setText(String.valueOf(subject.getSubjectName()));

                labelProf.setText("TestProfessor");
                labelSubject.setText("TestSubject");
            }


        }
    }
}
