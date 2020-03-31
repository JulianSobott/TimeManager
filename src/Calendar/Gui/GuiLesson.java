package Calendar.Gui;

import Calendar.Gui.NewLesson.ControllerLesson;
import Calendar.Logic.Position;
import Calendar.Logic.Subject;
import Calendar.Logic.Timetable;
import entryPoint.SceneLoader;
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
        this.subjectName.setWrapText(true);
        this.setAlignment(Pos.CENTER);

        updateDesign();
        generateContextMenu();
    }

    private void updateDesign() {

        this.setStyle("-fx-background-color: " + this.color);
        subjectName.setStyle("-fx-font-size: 1.5em; -fx-padding: 5 5 5 5; " +
                "-fx-background-color:" + this.color);

        lecturer.setStyle("-fx-padding: 5 5 5 5; -fx-background-color:" + this.color);
        subjectLocation.setStyle("-fx-padding: 5 5 5 5; -fx-background-color: " + this.color);
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
        updateDesign();

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

        MenuItem menuItemEditSubject = new MenuItem("Bearbeiten");
        generateEditLesson(menuItemEditSubject);
        MenuItem menuItemDelete = new MenuItem("Löschen");
        generateDeleteLessonEvent(menuItemDelete);

        contextMenu.getItems().addAll(menuItemEditSubject ,menuItemDelete);
        this.setOnMouseClicked(mouseEvent -> {

            contextMenu.show(this, Side.BOTTOM, -this.getHeight() *0.20, -this.getHeight() / 2);
        });
    }



    private void generateEditLesson(MenuItem menuItem){

        menuItem.setOnAction(actionEvent -> {

            SceneLoader sceneLoader = SceneLoader.getInstance();
            ControllerLesson controllerLesson = new ControllerLesson(gridPaneCalendar, this.timetable, this.gridPaneCalendar, this, controllerCalender);
            sceneLoader.loadSceneInNewWindowWithoutButtons(SceneLoader.CalendarScene.NEW_LESSON, controllerLesson, this, 0.2, 0.2);
        });
    }



    private void generateDeleteLessonEvent(MenuItem menuItem) {

        menuItem.setOnAction(actionEvent -> {

            Position position = new Position(GridPane.getRowIndex(this), GridPane.getColumnIndex(this));

            gridPaneCalendar.getChildren().remove(this);
            timetable.deleteLesson(position.getRow(), position.getCol());

            VBox vBox = controllerCalender.generateEmptyVBox(position.getCol(), position.getRow());
            gridPaneCalendar.add(vBox, position.getCol(), position.getRow());

        });
    }

}
