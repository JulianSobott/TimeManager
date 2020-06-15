package KanBan.Logic;

import java.util.List;

public interface IKanBan {

    public void addNote(Note note);

    public List<Note> getAllNotes();

    public boolean deleteNote(Note note);

    public Note getUrgentNote();

    public boolean finishNote(Note note);
}
