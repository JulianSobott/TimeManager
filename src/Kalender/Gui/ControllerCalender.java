package Kalender.Gui;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class ControllerCalender implements Initializable {

    @FXML
    private Label labelCourseOfStudies;

    @FXML
    private Label labelCurrentTimeAndDate;

    @FXML
    private GridPane gridPaneTimetable;

    private double cellPercentageWidth;

    private double numberOfDays = 6;
    private double numberOfLessons = 8;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        getTimeAndDate();
        generateGridPaneTimetable(gridPaneTimetable);
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

        for (int i = 0; i < numberOfDays; i++) {
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
}
