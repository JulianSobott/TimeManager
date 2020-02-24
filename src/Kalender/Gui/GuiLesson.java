package Kalender.Gui;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class GuiLesson extends HBox {

    Label subjectName;
    Label lecturer;
    Label subjectLocation;

    public GuiLesson(Label subjectName, Label lecturer, Label subjectLocation) {
        this.subjectName = subjectName;
        this.lecturer = lecturer;
        this.subjectLocation = subjectLocation;
        this.getChildren().addAll(this.subjectName, this.lecturer, this.subjectLocation);
    }

    public Label getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(Label subjectName) {
        this.subjectName = subjectName;
    }

    public Label getLecturer() {
        return lecturer;
    }

    public void setLecturer(Label lecturer) {
        this.lecturer = lecturer;
    }

    public Label getSubjectLocation() {
        return subjectLocation;
    }

    public void setSubjectLocation(Label subjectLocation) {
        this.subjectLocation = subjectLocation;
    }
}
