package Calendar.Gui;

import Calendar.Logic.Position;
import Calendar.Logic.Subject;
import Calendar.Logic.Timetable;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class GuiLesson extends VBox {

    Label subjectName;
    Label lecturer;
    Label subjectLocation;
    String color;

    GridPane gridPaneCalendar;
    ControllerCalender controllerCalender;
    Timetable timetable;

    public GuiLesson(Subject subject, String subjectLocation, GridPane pane, ControllerCalender calender, Timetable timetable) {

        this.subjectName = new Label(subject.getSubjectName());
        this.lecturer = new Label(subject.getProfessor());
        this.subjectLocation = new Label(subjectLocation);
        this.color = subject.getColor();
        this.getChildren().addAll(this.subjectName, this.lecturer, this.subjectLocation);

        this.gridPaneCalendar = pane;
        this.controllerCalender = calender;
        this.timetable = timetable;
        designGuiLesson();
    }

    private void designGuiLesson() {

        this.setId("SettingsLesson");
        this.setStyle("-fx-background-color: " + this.color);
        this.setAlignment(Pos.CENTER);

        subjectName.setStyle("-fx-font-size: 1.5em;");


        generateContextMenu();
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

    public Position deleteGuiLesson() {

        Position position = new Position(GridPane.getRowIndex(this), GridPane.getColumnIndex(this));
        gridPaneCalendar.getChildren().remove(this);
        return position;

    }

    private void generateContextMenu() {

        ContextMenu contextMenu = new ContextMenu();
        MenuItem menuItemDelete = new MenuItem("Löschen");
        generateDeleteLessonEvent(menuItemDelete);

        contextMenu.getItems().addAll(menuItemDelete);
        this.setOnMouseClicked(mouseEvent -> {

            contextMenu.show(this, Side.BOTTOM, -this.getHeight() / 2, -this.getHeight() / 2);
        });
    }

    private void generateDeleteLessonEvent(MenuItem menuItem) {

        menuItem.setOnAction(actionEvent -> {

            Position position = new Position(GridPane.getRowIndex(this), GridPane.getColumnIndex(this));

            gridPaneCalendar.getChildren().remove(this);
            timetable.deleteLesson(position.getRow(), position.getCol());

            VBox vBox = controllerCalender.generateEmptyVBox(position.getCol(), position.getRow());
            gridPaneCalendar.add( vBox ,position.getCol(), position.getRow());

        });
    }

}
