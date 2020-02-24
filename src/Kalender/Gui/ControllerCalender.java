package Kalender.Gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerCalender implements Initializable {

    @FXML
    private GridPane gridPaneTimetable;

    private double cellPercentageWidth;

    private double numberOfDays = 6;
    private double numberOfLessons = 8;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        generateGridPaneTimetable(gridPaneTimetable);
    }

    /**
     * ###################### GUI Initialization #######################################################################
     */

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
