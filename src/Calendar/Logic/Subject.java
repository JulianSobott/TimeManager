package Calendar.Logic;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Subject {

    private static int idGenerator = 0;

    private final int id;
    private ArrayList<Lesson> lessons = new ArrayList<>();
    private SimpleStringProperty color;
    private SimpleStringProperty professor;
    private SimpleStringProperty subjectName;


    public Subject(String professor, String subjectName) {

        this.professor = new SimpleStringProperty(professor);
        this.subjectName = new SimpleStringProperty (subjectName);
        this.id = idGenerator++;
    }

    public Subject(Color color, String professor, String subjectName) {

        this.color = new SimpleStringProperty (color.toString());
        this.professor = new SimpleStringProperty (professor);
        this.subjectName = new SimpleStringProperty (subjectName);
        this.id = idGenerator++;
    }


    public int getId() {
        return id;
    }

    public ArrayList<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(ArrayList<Lesson> lessons) {
        this.lessons = lessons;
    }

    public String getColor() {
        return color.get();
    }

    public SimpleStringProperty colorProperty() {
        return color;
    }

    public void setColor(String color) {
        this.color.set(color);
    }

    public String getProfessor() {
        return professor.get();
    }

    public SimpleStringProperty professorProperty() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor.set(professor);
    }

    public String getSubjectName() {
        return subjectName.get();
    }

    public SimpleStringProperty subjectNameProperty() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName.set(subjectName);
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", lessons=" + lessons +
                ", color=" + color +
                ", professor='" + professor + '\'' +
                ", subjectName='" + subjectName + '\'' +
                '}';
    }
}
