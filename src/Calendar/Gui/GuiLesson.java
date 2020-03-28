package Calendar.Gui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class GuiLesson extends VBox {

    Label subjectName;
    Label lecturer;
    Label subjectLocation;
    String color;


    public GuiLesson(String subjectName, String lecturer, String subjectLocation, String color) {

        this.subjectName = new Label(subjectName);
        this.lecturer = new Label(lecturer);
        this.subjectLocation = new Label(subjectLocation);
        this.color = color;
        this.getChildren().addAll(this.subjectName, this.lecturer, this.subjectLocation);

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
}
