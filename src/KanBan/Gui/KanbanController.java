package KanBan.Gui;

import KanBan.Logic.IKanBan;
import KanBan.Logic.KanBan;
import KanBan.Logic.Note;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class KanbanController implements Initializable {

    @FXML
    private Pane paneUrgentNote;
    @FXML
    private GridPane paneTodo;
    @FXML
    private GridPane paneFinished;

    private IKanBan kanban;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO set kanban and remove if
        if (kanban != null) {
            kanban.addNote(new Note("Hello world", 2, new Date()));

            // Urgent Note
            paneUrgentNote.getChildren().remove(0, paneUrgentNote.getChildren().size());
            //paneUrgentNote.getChildren().add(new NoteWidget(kanban.getUrgentNote()));

            // Notes
            // TODO: dynamic size
            int i = 0;
            for(Note note : kanban.getAllNotes()) {
                if (note.isNoteFinished()) {
                    paneTodo.addRow(i++, new NoteWidget(note));
                }
            }

            i = 0;
            for(Note note : kanban.getAllNotes()) {
                if (!note.isNoteFinished()) {
                    paneFinished.addRow(i++, new NoteWidget(note));
                }
            }
        }
    }
}
