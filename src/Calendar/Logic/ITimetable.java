package Calendar.Logic;

import javafx.collections.ObservableList;

import java.util.ArrayList;

public interface ITimetable {


    public void addSubject(Subject subject);

    public void deleteSubject(Subject subject);

    public ObservableList<Subject> getSubjectList();

    public void addLesson(Lesson lesson, int row, int col);

    public void deleteLesson(Lesson lesson, int row, int col);

}
