package Calendar.Gui.EditLesson;

import Calendar.Gui.ControllerCalender;
import Calendar.Gui.NewLesson.ControllerLesson;
import Calendar.Logic.Lesson;
import Calendar.Logic.Position;
import Calendar.Logic.Subject;
import Calendar.Logic.Timetable;
import entryPoint.SceneLoader;
import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerEditLesson implements Initializable {


    @FXML
    private AnchorPane anchorPaneEditLesson;


    @FXML
    private TableView<Subject> tableViewSubjects;

    @FXML
    private TableColumn colColor;

    @FXML
    private TableColumn colSubjectName;

    @FXML
    private TableColumn colLecturer;


    @FXML
    private Button buttonSaveAndBack;




    private ObservableList<Subject> subjectObservableList;

    private final Node nodeTabCalendar;
    private final Timetable timetable;
    private final GridPane gridPaneTimetable;
    private final VBox vBoxLesson;
    private final ControllerCalender controllerCalender;



    public ControllerEditLesson(Node node, Timetable timetable, GridPane gridPane, VBox vBoxLesson, ControllerCalender calender){

        this.nodeTabCalendar = node;
        this.timetable = timetable;
        this.gridPaneTimetable = gridPane;
        this.vBoxLesson = vBoxLesson;
        this.controllerCalender = calender;

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.subjectObservableList = timetable.getSubjectList();
        tableViewSubjects.setItems(this.subjectObservableList);
        bindDataToTableView();

    }




    /**
     *  ########################## Table View ##########################################################################
     */


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

        int index = 0;
        for (Subject s : subjectObservableList) {

            if (subject.getId() == s.getId())
                break;
            index++;
        }

        tableViewSubjects.getSelectionModel().select(index);
    }


    private Position getPosition(VBox vBox) {

        return new Position(GridPane.getRowIndex(vBox), GridPane.getColumnIndex(this.vBoxLesson));
    }




    /**
     * ###################################### Windows Navigation #######################################################
     */



    @FXML
    private void loadEditLessonWindow() {

        SceneLoader sceneLoader = SceneLoader.getInstance();
        sceneLoader.loadAnimationPopupWindow(buttonSaveAndBack, anchorPaneEditLesson,
                SceneLoader.CalendarScene.NEW_LESSON, new ControllerLesson(nodeTabCalendar,timetable, gridPaneTimetable,vBoxLesson,controllerCalender));

    }




}
