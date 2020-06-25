package Calendar.Gui.EditLesson;

import Calendar.Gui.ControllerCalender;
import Calendar.Gui.NewLesson.ControllerLesson;
import Calendar.Logic.Timetable;
import entryPoint.SceneLoader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerEditLesson implements Initializable {


    @FXML
    private AnchorPane anchorPaneEditLesson;

    @FXML
    private Button buttonSaveAndBack;

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
