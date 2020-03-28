package Calendar.Gui.NewLesson;

import Calendar.Gui.GuiLesson;
import Calendar.Logic.Lesson;
import Calendar.Logic.Subject;
import Calendar.Logic.Timetable;
import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.MotionBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerLesson implements Initializable {

    @FXML
    private AnchorPane anchorPaneLesson;

    @FXML
    private Button buttonSave;

    @FXML
    private Button buttonClose;

    @FXML
    private TableView<Subject> tableViewSubjects;

    @FXML
    private TableColumn colColor;

    @FXML
    private TableColumn colSubjectName;

    @FXML
    private TableColumn colLecturer;


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
    private GridPane gridPaneTimetable;
    private VBox vBoxEmpty;

    private ObservableList<Subject> subjectObservableList;
    private Subject selectedSubject;
    private Timetable timetable;

    public ControllerLesson(Node node, Timetable timetable, GridPane gridPane, VBox vBoxEmpty) {

        this.nodeTabCalendar = node;
        this.timetable = timetable;
        this.gridPaneTimetable = gridPane;
        this.vBoxEmpty = vBoxEmpty;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.subjectObservableList = timetable.getSubjectList();
        tableViewSubjects.setItems(this.subjectObservableList);
        bindDataToTableView();


        MotionBlur motionBlur = new MotionBlur();
        nodeTabCalendar.setEffect(motionBlur);
        makeFadeInTransition(0, 1);
    }


    private void bindDataToTableView() {

        colSubjectName.setCellValueFactory(
                new PropertyValueFactory<Subject, String>("subjectName"));
        colLecturer.setCellValueFactory(
                new PropertyValueFactory<Subject, String>("professor"));
        colColor.setCellValueFactory(
                new PropertyValueFactory<Subject, String>("paneSubjectColor"));

    }


    /**
     * ################ methods of the individual buttons ##############################################################
     */


    private void clearFields() {

        textFieldProfessor.clear();
        textFieldSubject.clear();
    }


    @FXML
    private void addNewSubject() {

        String lessonColor = colorToHexCode(colorPickerSubjectColor.getValue());

        Subject subject = new Subject(lessonColor, textFieldProfessor.getText(), textFieldSubject.getText());
        clearFields();

        subjectObservableList.add(subject);
        timetable.addSubject(subject);
    }

    private String colorToHexCode(Color color) {

        String lessonColor = "#" + color.toString().substring(2, 8);
        return lessonColor;
    }


    @FXML
    private void deleteSubject() {

        Subject subject = tableViewSubjects.getSelectionModel().getSelectedItem();

        subjectObservableList.remove(subject);
        timetable.deleteSubject(subject);
    }


    @FXML
    private void editSubject() {

        this.selectedSubject = tableViewSubjects.getSelectionModel().getSelectedItem();

        textFieldSubject.setText(this.selectedSubject.getSubjectName());
        textFieldProfessor.setText(this.selectedSubject.getProfessor());
        colorPickerSubjectColor.setValue(Color.web(this.selectedSubject.getColor()));
    }


    @FXML
    private void saveSubjectChanges() {

        if (this.selectedSubject != null) {
            this.selectedSubject.setSubjectName(textFieldSubject.getText());
            this.selectedSubject.setProfessor(textFieldProfessor.getText());
            this.selectedSubject.setColor(colorToHexCode(colorPickerSubjectColor.getValue()));

            selectedSubject.notifyAllObservers();
            this.selectedSubject = null;
        }

        tableViewSubjects.getSelectionModel().select(null);
        clearFields();
    }


    /**
     * ###################################### Windows Navigation #######################################################
     */

    @FXML
    private void closeSubjectWindow() {

        nodeTabCalendar.setEffect(null);
        makeFadeInTransition(1, 0);
    }

    @FXML
    private void AddSubjectToTimetable() {

        int row = GridPane.getRowIndex(this.vBoxEmpty);
        int col = GridPane.getColumnIndex(this.vBoxEmpty);

        Subject subject = tableViewSubjects.getSelectionModel().getSelectedItem();
        if (subject != null && textFieldCourseLocation.getText().isEmpty() == false) {

            GuiLesson guiLesson = new GuiLesson(subject.getSubjectName(), subject.getProfessor(),
                    textFieldCourseLocation.getText(), subject.getColor());

            Lesson lesson = new Lesson(subject, guiLesson ,textFieldCourseLocation.getText());
            subject.registriesObservers(lesson);
            timetable.addLesson(lesson, row, col);

            gridPaneTimetable.getChildren().remove(vBoxEmpty);
            gridPaneTimetable.add(guiLesson, col, row);

            closeSubjectWindow();
        }
    }


    private void makeFadeInTransition(int startValue, int targetValue) {

        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(700));
        fadeTransition.setNode(anchorPaneLesson);
        fadeTransition.setFromValue(startValue);
        fadeTransition.setToValue(targetValue);
        fadeTransition.play();
    }

}
