package Calendar.Gui.Settings;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerCalendarSettings implements Initializable {

    @FXML
    private AnchorPane anchorPaneSettings;

    @FXML
    private ComboBox comboBoxNumberOfDays;

    @FXML
    private ComboBox comboBoNumberOfLessons;


    @FXML
    private TextField textFieldShortBreakMin;

    @FXML
    private TextField textFieldLunchBreakMin;

    @FXML
    private ComboBox comboBoxLunchBreakAfterNumberOfLessons;

    @FXML
    private TextField textFieldDurationOfLectures;

    private int numberOfDays;
    private int numberOfLessons;
    private int durationOfLectures;

    public ControllerCalendarSettings(int numberOfDays, int numberOfLessons, int durationOfLectures) {

        this.numberOfDays = numberOfDays;
        this.numberOfLessons = numberOfLessons;
        this.durationOfLectures = durationOfLectures;
    }

    private ObservableList<Integer> numberOfDaysObservableList = FXCollections.observableArrayList();
    private ObservableList<Integer> numberOfLessonsObservableList = FXCollections.observableArrayList();
    private ObservableList<Integer> lunchBreakAfterNumberOfLessonsObservableList = FXCollections.observableArrayList();





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        comboBoxNumberOfDays.setItems(numberOfDaysObservableList);
        comboBoNumberOfLessons.setItems(numberOfLessonsObservableList);
        comboBoxLunchBreakAfterNumberOfLessons.setItems(lunchBreakAfterNumberOfLessonsObservableList);

        makeFadeInTransition(0, 1, false);
        addElementsToTheObservableList(numberOfDaysObservableList, numberOfDays,1);
        addElementsToTheObservableList(numberOfLessonsObservableList, numberOfLessons,1);
        addElementsToTheObservableList(lunchBreakAfterNumberOfLessonsObservableList, 90, 10);
    }


    /**
     * ##################################### generate Gui elements #####################################################
     */


    private void addElementsToTheObservableList(ObservableList observableList , int maximum , int interval){

        for (int i = 0; i <= maximum ; i = i + interval) {

            observableList.add(i);
        }

    }







    /**
     * ################################   Window Navigation #############################################################
     */

    private void makeFadeInTransition(int startValue, int targetValue, boolean exit) {

        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(800));
        fadeTransition.setNode(anchorPaneSettings);
        fadeTransition.setFromValue(startValue);
        fadeTransition.setToValue(targetValue);
        fadeTransition.play();
    }


    @FXML
    private void closeWindow() throws InterruptedException {

        makeFadeInTransition(1, 0, true);

    }


}


