package Calendar.Gui.NewLesson;

import Calendar.Gui.ControllerCalender;
import Calendar.Gui.EditLesson.ControllerEditLesson;
import Calendar.Gui.GuiLesson;
import Calendar.Logic.*;
import GuiElements.AutoCompleteTextField;
import entryPoint.SceneLoader;
import javafx.animation.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.MotionBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
    private Button buttonEditLesson; // Button to Change Scene


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
    private AutoCompleteTextField textFieldCourseLocation;

    @FXML
    private ColorPicker colorPickerSubjectColor;

    @FXML
    private Button buttonAddSubject;

    @FXML
    private Button buttonDeleteSubject;


    private Node nodeTabCalendar;
    private GridPane gridPaneTimetable;
    private VBox vBoxLesson;


    private ObservableList<Subject> subjectObservableList;
    private Subject selectedSubject;
    private Timetable timetable;
    private ControllerCalender controllerCalender;

    public ControllerLesson(Node node, Timetable timetable, GridPane gridPane, VBox vBoxLesson, ControllerCalender calender) {

        this.nodeTabCalendar = node;
        this.timetable = timetable;
        this.gridPaneTimetable = gridPane;
        this.vBoxLesson = vBoxLesson;
        this.controllerCalender = calender;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.subjectObservableList = timetable.getSubjectList();
        tableViewSubjects.setItems(this.subjectObservableList);
        bindDataToTableView();
        textFieldCourseLocation.getEntries().addAll(timetable.getLocationList());

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


    public void setData() {

        Position position = getPosition(this.vBoxLesson);
        Lesson lesson = timetable.getLesson(position.getRow(), position.getCol());
        Subject subject = timetable.getSubject(lesson);

        textFieldCourseLocation.setText(lesson.getClassroom());
        textFieldCourseLocation.hidePopup();

        int index = 0;
        for (Subject s : subjectObservableList) {

            if (subject.getId() == s.getId())
                break;
            index++;
        }

        tableViewSubjects.getSelectionModel().select(index);
        editSubject();
    }


    private Position getPosition(VBox vBox) {

        return new Position(GridPane.getRowIndex(vBox), GridPane.getColumnIndex(this.vBoxLesson));
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

        return "#" + color.toString().substring(2, 8);
    }


    @FXML
    private void deleteSubject() {

        Subject subject = tableViewSubjects.getSelectionModel().getSelectedItem();
        ArrayList<Position> positionArrayList;

        if (subject != null) {
            positionArrayList = subject.deleteAllObject();
            subjectObservableList.remove(subject);
            timetable.deleteSubject(subject);

            for (Position p : positionArrayList) {

                VBox vBox = controllerCalender.generateEmptyVBox(p.getCol(), p.getRow());
                gridPaneTimetable.add(vBox, p.getCol(), p.getRow());
            }
        }
    }


    @FXML
    private void editSubject() {

        this.selectedSubject = tableViewSubjects.getSelectionModel().getSelectedItem();

        if (selectedSubject != null) {
            textFieldSubject.setText(this.selectedSubject.getSubjectName());
            textFieldProfessor.setText(this.selectedSubject.getProfessor());
            colorPickerSubjectColor.setValue(Color.web(this.selectedSubject.getColor()));
        }
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
    private void loadEditLessonWindow() {

        SceneLoader sceneLoader = SceneLoader.getInstance();
        sceneLoader.loadAnimationPopupWindow(buttonEditLesson, anchorPaneLesson,
                SceneLoader.CalendarScene.EDIT_LESSON, new ControllerEditLesson(nodeTabCalendar, timetable, gridPaneTimetable, vBoxLesson, controllerCalender));

    }


    @FXML
    private void closeSubjectWindow() {

        nodeTabCalendar.setEffect(null);
        makeFadeInTransition(1, 0);
    }

    @FXML
    private void AddSubjectToTimetable() {

        Position position = getPosition(this.vBoxLesson);

        Subject subject = tableViewSubjects.getSelectionModel().getSelectedItem();
        if (subject != null && !textFieldCourseLocation.getText().isEmpty()) {

            boolean tutorial = false;
            if (checkBoxTutorial.isSelected()) {
                tutorial = true;
            }
            generateNewLesson(subject, position, tutorial);

            if (checkBoxDoubleHour.isSelected())
                generateNewLesson(subject, new Position(position.getRow() + 1, position.getCol()), tutorial);
            closeSubjectWindow();
        }

    }

    private void generateNewLesson(Subject subject, Position position, Boolean tutorial) {

        GuiLesson guiLesson = new GuiLesson(subject, textFieldCourseLocation.getText(),
                this.gridPaneTimetable, this.controllerCalender, this.timetable, tutorial);

        Lesson lesson = new Lesson(subject, guiLesson, textFieldCourseLocation.getText(), tutorial);
        subject.registriesObservers(lesson);
        timetable.addLesson(lesson, position.getRow(), position.getCol());

        VBox emptyLesson = timetable.getEmptyLesson(position);
        gridPaneTimetable.getChildren().remove(emptyLesson);
        gridPaneTimetable.add(guiLesson, position.getCol(), position.getRow());

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
