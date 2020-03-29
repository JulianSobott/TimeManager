package Calendar.Gui;

import Calendar.Logic.Position;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class GuiLesson extends VBox {

    Label subjectName;
    Label lecturer;
    Label subjectLocation;
    String color;

    GridPane gridPaneCalendar;


    public GuiLesson(String subjectName, String lecturer, String subjectLocation, String color, GridPane pane) {

        this.subjectName = new Label(subjectName);
        this.lecturer = new Label(lecturer);
        this.subjectLocation = new Label(subjectLocation);
        this.color = color;
        this.getChildren().addAll(this.subjectName, this.lecturer, this.subjectLocation);

        this.gridPaneCalendar = pane;
        designGuiLesson();
    }

    private void designGuiLesson() {

        this.setId("SettingsLesson");
        this.setStyle("-fx-background-color: " + this.color);
        this.setAlignment(Pos.CENTER);
    }


    public void setSubjectName(String subjectName) {
        this.subjectName.setText(subjectName);
    }

    public void setLecturer(String lecturer) {
        this.lecturer.setText(lecturer);
    }

    public void setSubjectLocation(String subjectLocation) {
        this.subjectLocation.setText(subjectLocation);
    }

    public void setColor(String color) {

        this.color = color;
        this.setStyle("-fx-background-color: " + this.color);
    }

    public void updateUserInformation(String subjectName, String lecturer, String subjectLocation, String color) {

        setSubjectName(subjectName);
        setLecturer(lecturer);
        setSubjectLocation(subjectLocation);
        setColor(color);

    }

    public Position deleteGuiLesson(){

        Position position = new Position(GridPane.getRowIndex(this), GridPane.getColumnIndex(this));
        gridPaneCalendar.getChildren().remove(this);
        return position;

    }

}
