package Calendar.Logic;

import javafx.collections.ObservableList;

public interface ITimetable {


    public void addSubject(Subject subject);

    public void deleteSubject(Subject subject);

    public ObservableList<Subject> getSubjectList();

    public void addLesson(Lesson lesson);

    public void deleteLesson(Lesson lesson);

    public void getAllLessonsFromOneSubject(Subject subject);

}
