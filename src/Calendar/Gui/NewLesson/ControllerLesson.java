package Calendar.Gui.NewLesson;

import Calendar.Logic.Subject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.MotionBlur;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerLesson implements Initializable {

    @FXML
    private Button buttonSave;

    @FXML
    private Button buttonClose;

    @FXML
    private ListView listViewSubjects;

    @FXML
    private CheckBox checkBoxTutorial;

    @FXML
    private CheckBox checkBoxDoubleHour;

    @FXML
    private CheckBox checkBoxInterval;

    @FXML
    private TextField textFieldProfessor;

    @FXML
    private TextField textFieldSubject;

    @FXML
    private TextField textFieldCourseLocation;

    @FXML
    private ColorPicker colorPickerSubjectColor;

    @FXML
    private Button buttonAddSubject;

    @FXML
    private Button buttonDeleteSubject;


    private Node nodeTabCalendar;

    private ObservableList<Subject> subjects = FXCollections.observableArrayList();


    public ControllerLesson(Node node) {

        this.nodeTabCalendar = node;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        listViewSubjects.setItems(subjects);
        listViewSubjects.setCellFactory(studentListView -> new ControllerListCellLesson());

        MotionBlur motionBlur = new MotionBlur();
        nodeTabCalendar.setEffect(motionBlur);
    }

    @FXML
    public void addNewSubject() {

        Subject subject = new Subject(colorPickerSubjectColor.getValue(),textFieldProfessor.getText(), textFieldSubject.getText());

        textFieldProfessor.clear();
        textFieldSubject.clear();
        subjects.add(subject);

    }

}
