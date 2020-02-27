package Calendar.Gui.NewLesson;

import Calendar.Logic.Subject;
import javafx.animation.FadeTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.MotionBlur;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
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

    @FXML
    private Button buttonSaveChanges;


    private Node nodeTabCalendar;

    private ObservableList<Subject> subjectObservableList = FXCollections.observableArrayList();


    public ControllerLesson(Node node) {

        this.nodeTabCalendar = node;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        listViewSubjects.setItems(subjectObservableList);
        listViewSubjects.setCellFactory(studentListView -> new ControllerListCellLesson());

        MotionBlur motionBlur = new MotionBlur();
        nodeTabCalendar.setEffect(motionBlur);
        makeFadeInTransition(0,1);

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

        Subject subject = new Subject(colorPickerSubjectColor.getValue(), new SimpleStringProperty(textFieldProfessor.getText()), new SimpleStringProperty(textFieldSubject.getText()));
        clearFields();
        subjectObservableList.add(subject);

    }

    @FXML
    private void deleteSubject() {

        Subject subject = (Subject) listViewSubjects.getSelectionModel().getSelectedItem();
        subjectObservableList.removeAll(subject);
    }


    @FXML
    private void editSubject() {

        Subject subject = (Subject) listViewSubjects.getSelectionModel().getSelectedItem();
        textFieldSubject.setText(subject.getSubjectName());
        textFieldProfessor.setText(subject.getProfessor());
        colorPickerSubjectColor.setValue(subject.getColor());
    }

    /*   TODO: Hier muss noch ein Listener eingebaut werden um änderungen anzeigen zu können !!!   */

    @FXML
    private void saveSubjectChanges() {

        Subject subject = (Subject) listViewSubjects.getSelectionModel().getSelectedItem();
        subject.setSubjectName(textFieldSubject.getText());
        subject.setProfessor(textFieldProfessor.getText());
        subject.setColor(colorPickerSubjectColor.getValue());

        listViewSubjects.refresh();
        clearFields();
    }


    /**
     *################### Windows Navigation ###########################################################################
     */

    @FXML
    private void closeSubjectWindow() {

        nodeTabCalendar.setEffect(null);
        makeFadeInTransition(1,0);

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
