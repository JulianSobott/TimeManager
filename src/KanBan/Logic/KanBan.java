package KanBan.Logic;

import java.util.ArrayList;
import java.util.List;

public class KanBan implements IKanBan{

    private List<Note> openNotes = new ArrayList<>();
    private List<Note> doneNotes = new ArrayList<>();

    @Override
    public void addNote(Note note) {
        openNotes.add(note);
        InsertionSort.sort(openNotes);
    }

    @Override
    public List<Note> getAllNotes() {
        return null;
    }

    @Override
    public boolean deleteNote(Note note) {
        return false;
    }

    @Override
    public Note getUrgentNote() {
        return null;
    }

    @Override
    public boolean finishNote(Note note) {
        return false;
    }
}
