package Calendar.Logic;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Subject {

    private ArrayList<Lesson> lessons = new ArrayList<>();
    private Color color;
    private String professor;
    private String subjectName;


    public Subject(String professor, String subjectName) {
        this.professor = professor;
        this.subjectName = subjectName;
    }


    public Subject(Color color, String professor, String subjectName) {
        this.color = color;
        this.professor = professor;
        this.subjectName = subjectName;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
