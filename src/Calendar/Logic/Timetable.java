package Calendar.Logic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Timetable implements ITimetable {

    private int days = 8;
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
    public Subject getSubject(Lesson lesson) {

        Subject subject = null ;
        for (Subject s: subjects) {

            if(s.getId() == lesson.getSubjectID())
                subject = s;
        }
        return subject;
    }


    @Override
    public void deleteSubject(Subject subject) {

        subjects.remove(subject);
        deleteAllLessonsForSubject(subject);
    }


    //  [] [] erst Zeile dann Spalte
    private void deleteAllLessonsForSubject(Subject subject){

        for (int i = 1; i < lecturesPerDay; i++) {
            for (int j = 1; j < days; j++) {

                if( timetableArray[i][j] != null && timetableArray[i][j].getSubjectID() == subject.getId()){

                    timetableArray[i][j] = null ;
                }
            }
        }
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
    public void addLesson(Lesson lesson, int row, int col) {

        timetableArray[row][col] = lesson;
    }

    @Override
    public Lesson getLesson(int row, int col) {

        return timetableArray[row][col];
    }

    @Override
    public void deleteLesson(int row, int col) {

        timetableArray[row][col] = null;
    }

}
