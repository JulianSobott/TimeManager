package Calendar.Gui.Settings;

import Calendar.Logic.SettingsCalendar;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerCalendarSettings implements Initializable {

    @FXML
    private AnchorPane anchorPaneSettings;

    @FXML
    private ComboBox<Integer> comboBoxNumberOfDays;

    @FXML
    private ComboBox<Integer> comboBoNumberOfLessons;


    @FXML
    private ComboBox<Integer> comboBoxShortBreakMin;

    @FXML
    private ComboBox<Integer> comboBoxLunchBreakMin;

    @FXML
    private ComboBox<Integer> comboBoxLunchBreakAfterNumberOfLessons;

    @FXML
    private ComboBox<Integer> comboBoxDurationOfLectures;

    private int numberOfDays;
    private int numberOfLessons;
    private int shortBreak;
    private int lunchBreakMin;
    private int lunchBreakAfterNumberOfLessons;
    private int durationOfLectures;


    private ObservableList<Integer> numberOfDaysObservableList = FXCollections.observableArrayList();
    private ObservableList<Integer> numberOfLessonsObservableList = FXCollections.observableArrayList();

    private ObservableList<Integer> shortBreakObservableList = FXCollections.observableArrayList();
    private ObservableList<Integer> lunchBreakObservableList = FXCollections.observableArrayList();
    private ObservableList<Integer> lunchBreakAfterNumberOfLessonsObservableList = FXCollections.observableArrayList();
    private ObservableList<Integer> durationOfLecturesObservableList = FXCollections.observableArrayList();


    public ControllerCalendarSettings() {

        SettingsCalendar settings = SettingsCalendar.getInstance();
        this.numberOfDays = settings.getNumberOfDays();
        this.numberOfLessons = settings.getNumberOfLessons();
        this.shortBreak = (int) settings.getShortBreakMin();
        this.lunchBreakMin = (int) settings.getLunchBreakMin();
        this.lunchBreakAfterNumberOfLessons = settings.getLunchBreakAfterNumberOfLessons();
        this.durationOfLectures = (int) settings.getDurationOfLectures();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        comboBoxNumberOfDays.setItems(numberOfDaysObservableList);
        comboBoNumberOfLessons.setItems(numberOfLessonsObservableList);

        comboBoxShortBreakMin.setItems(shortBreakObservableList);
        comboBoxLunchBreakMin.setItems(lunchBreakObservableList);
        comboBoxLunchBreakAfterNumberOfLessons.setItems(lunchBreakAfterNumberOfLessonsObservableList);
        comboBoxDurationOfLectures.setItems(durationOfLecturesObservableList);

        makeFadeInTransition(0, 1, false);
        filComboboxWithContent();
        setSelectedSettingsInComboBoxes();

    }

    /**
     * ##################################### Settings Calendar ########################################################
     */

    private void updateSettings(){

    }

    // -1 ist zum umrechen bezüglich des Index


    private void setSelectedSettingsInComboBoxes(){

        SettingsCalendar settings = SettingsCalendar.getInstance();
        comboBoxNumberOfDays.getSelectionModel().select(settings.getNumberOfDays()-1);
        comboBoNumberOfLessons.getSelectionModel().select(settings.getNumberOfLessons()-1);

        comboBoxShortBreakMin.getSelectionModel().select((int) settings.getShortBreakMin() / 5 - 1);
    }


    /**
     * ##################################### generate Gui elements #####################################################
     */


    // TODO: Invervallschritte müssen in die Settings ausgelagert werden !!!

    private void filComboboxWithContent() {

        addElementsToTheObservableList(numberOfDaysObservableList, 7, 1);
        addElementsToTheObservableList(numberOfLessonsObservableList, 8, 1);

        addElementsToTheObservableList(shortBreakObservableList, 25, 5);
        addElementsToTheObservableList(lunchBreakObservableList, 70, 5);
        addElementsToTheObservableList(lunchBreakAfterNumberOfLessonsObservableList, 5, 1);
        addElementsToTheObservableList(durationOfLecturesObservableList, 100, 10);
    }


    private void addElementsToTheObservableList(ObservableList<Integer> observableList, int maximum, int interval) {

        for (int i = 0; i <= maximum; i = i + interval) {

            if (i == 0)
                continue;
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
    private void closeWindow() {

        makeFadeInTransition(1, 0, true);

    }


}


