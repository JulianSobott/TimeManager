package Calendar.Logic;

import javafx.collections.ObservableList;

public interface ITimetable {


    public void addSubject(Subject subject);

    public void deleteSubject(Subject subject);

    public ObservableList<Subject> getSubjectList();

    public void addLesson(Lesson lesson, int row, int col);

    public void deleteLesson(int row, int col);

}