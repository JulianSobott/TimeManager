package Calendar.Logic;

import java.util.ArrayList;

public class Subject {

    private ArrayList<Lesson> lessons = new ArrayList<>();
    private String color;
    private String professor;
    private String subjectName;


    public Subject(String professor, String subjectName) {
        this.professor = professor;
        this.subjectName = subjectName;
    }


    public Subject(String color, String professor, String subjectName) {
        this.color = color;
        this.professor = professor;
        this.subjectName = subjectName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
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
