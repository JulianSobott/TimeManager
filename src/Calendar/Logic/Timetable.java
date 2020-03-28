package Calendar.Logic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Timetable implements ITimetable {

    private int days = 7;
    private int lecturesPerDay = 10;

    ArrayList<Subject> subjects = new ArrayList<>();
    Lesson[][] timetableArray = new Lesson[lecturesPerDay][days];


    public Timetable() {


    }


    @Override
    public void addSubject(Subject subject) {

        subjects.add(subject);
    }

    @Override
    public void deleteSubject(Subject subject) {

        subjects.remove(subject);
    }

    @Override
    public ObservableList<Subject> getSubjectList() {

        ObservableList<Subject> subjectObservableList = FXCollections.observableArrayList();
        for (Subject subject : subjects) {

            subjectObservableList.add(subject);
        }
        return subjectObservableList;
    }

    @Override
    public void addLesson(Lesson lesson) {

    }

    @Override
    public void deleteLesson(Lesson lesson) {

    }

    @Override
    public void getAllLessonsFromOneSubject(Subject subject) {

    }
}
