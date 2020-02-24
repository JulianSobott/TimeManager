package Kalender.Gui;

import Kalender.Logic.Weekdays;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerCalender implements Initializable {

    @FXML
    private Label labelCourseOfStudies;

    @FXML
    private Label labelCurrentTimeAndDate;

    @FXML
    private GridPane gridPaneTimetable;

    private double cellPercentageWidth;

    private int numberOfDays = 6;
    private int numberOfLessons = 8;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        getTimeAndDate();
        generateGridPaneTimetable(gridPaneTimetable);
        generateLabelsDays();
        generateEmptyLessons();
    }

    /**
     * ###################### GUI Initialization #######################################################################
     */


    // neuer Thread / Task der immer die Uhrzeit abfragt und updatet
    // THread muss in eine Liste eingetragen werden, um sauber mit dem Programm beendet werden zu können.. !!!
    private void getTimeAndDate() {

    }


    private void generateGridPaneTimetable(GridPane gridPane) {

        cellPercentageWidth = 100 / numberOfDays;

        for (int i = 0; i <= numberOfDays; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setPercentWidth(cellPercentageWidth);
            gridPane.getColumnConstraints().add(col);
        }

        for (int i = 0; i < numberOfLessons; i++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(cellPercentageWidth);
            gridPane.getRowConstraints().add(row);
        }
    }


    /**
     * generate the labels for the Timetable
     */

    private void generateLabelsDays() {

        int dayCounter = 1;
        for (Weekdays dayName : Weekdays.values()) {
            Label labelDay = new Label(dayName.toString());

            gridPaneTimetable.add(labelDay, dayCounter, 0);
            GridPane.setHalignment(labelDay, javafx.geometry.HPos.CENTER);
            dayCounter++;
            if (dayCounter > numberOfDays) break;
        }
    }


    /**
     * generate empty Lessons for the timetable
     */

    private void generateEmptyLessons(){

        for (int i = 1; i <= numberOfDays; i++) {
            for (int j = 1; j < numberOfLessons; j++) {

                VBox vBoxEmpty = generateEmptyVBox(i, j);
                gridPaneTimetable.add(vBoxEmpty, i, j);
            }
        }
    }

    private VBox generateEmptyVBox(int day, int block) {

        VBox VBoxLessonBasicLayout = new VBox();
        VBoxLessonBasicLayout.setBackground(new Background(new BackgroundFill(Color.rgb(day * 10, block * 15, 130),
                new CornerRadii(13),
                new Insets(0.0, 0.0, 0.0, 0.0))));

               return VBoxLessonBasicLayout;
    }

}
