package Kalender.Gui;

import Kalender.Logic.Weekdays;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.awt.*;
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



}
