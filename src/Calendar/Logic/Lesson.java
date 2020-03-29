package Calendar.Logic;

import Calendar.Gui.GuiLesson;
import javafx.scene.layout.GridPane;

public class Lesson implements IObserver {

        private String classroom;
        private int subjectID;

        private final ISubject iSubject;
        private GuiLesson guiLesson;
        private GridPane gridPaneCalendar;


        public Lesson(ISubject iSubject , GuiLesson guiLesson , String classroom, GridPane gridPane) {

                Subject subject = (Subject)iSubject;

                this.classroom = classroom;
                this.subjectID = subject.getId();
                this.iSubject = iSubject;
                this.guiLesson = guiLesson;
                this.gridPaneCalendar = gridPane;
        }

        public String getClassroom() {
                return classroom;
        }

        public void setClassroom(String classroom) {
                this.classroom = classroom;
        }

        public int getSubjectID() {
                return subjectID;
        }

        public void setSubjectID(int subjectID) {
                this.subjectID = subjectID;
        }


        @Override
        public void update() {

               Subject subject = (Subject)iSubject;
               guiLesson.updateUserInformation(subject.getSubjectName(), subject.getProfessor(), this.classroom, subject.getColor());
        }

        @Override
        public void delete() {

            guiLesson.deleteGuiLesson();
        }
}
