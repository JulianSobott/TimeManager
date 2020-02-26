package Calendar.Gui.NewLesson;

import Calendar.Logic.Subject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.MotionBlur;
import javafx.stage.Stage;

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
    private Subject selectedSubject = new Subject(new SimpleStringProperty(), new SimpleStringProperty());

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

        textFieldSubject.textProperty().bindBidirectional(selectedSubject.subjectNameProperty());
    }


    /**
     * ################ methods of the individual buttons ##############################################################
     */


    @FXML
    private void addNewSubject() {

        Subject subject = new Subject(colorPickerSubjectColor.getValue(), new SimpleStringProperty(textFieldProfessor.getText()) , new SimpleStringProperty(textFieldSubject.getText()));
        textFieldProfessor.clear();
        textFieldSubject.clear();
        subjectObservableList.add(subject);

    }

    @FXML
    private void deleteSubject(){

        Subject subject = (Subject) listViewSubjects.getSelectionModel().getSelectedItem();
        subjectObservableList.removeAll(subject);
    }


    @FXML
    private void editSubject(){

        this.selectedSubject = (Subject) listViewSubjects.getSelectionModel().getSelectedItem();
        int a = 0;

    }


    @FXML
    private void closeSubjectWindow(){

        Stage stage = (Stage) buttonClose.getScene().getWindow();
        nodeTabCalendar.setEffect(null);
        stage.close();

    }

}
