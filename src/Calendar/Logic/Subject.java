package Calendar.Logic;

import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Subject {

    private static int idGenerator = 0;

    private final int id;
    private ArrayList<Lesson> lessons = new ArrayList<>();
    private Color color;
    private StringProperty professor;
    private StringProperty subjectName;



    public Subject(StringProperty professor, StringProperty subjectName) {
        this.professor = professor;
        this.subjectName = subjectName;
        this.id = idGenerator++;
    }

    public Subject(Color color, StringProperty professor, StringProperty subjectName) {
        this.color = color;
        this.professor = professor;
        this.subjectName = subjectName;
        this.id = idGenerator++;
    }


    public int getId() {
        return id;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getProfessor() {
        return professor.get();
    }

    public StringProperty professorProperty() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor.set(professor);
    }

    public String getSubjectName() {
        return subjectName.get();
    }

    public StringProperty subjectNameProperty() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName.set(subjectName);
    }
}
