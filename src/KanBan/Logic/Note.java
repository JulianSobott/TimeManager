package KanBan.Logic;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Note {

    private String title;
    private int stepCount;
    private List<String> stepTitles = new ArrayList<>();
    private List<String> stepDescriptions = new ArrayList<>();
    private List<Boolean> stepStatus = new ArrayList<>();
    private Date dueDate;
    private List<File> attachments = new ArrayList<>();
    private boolean noteStatus;

    public Note(String title, int stepCount, Date dueDate) {
        this.title = title;
        this.stepCount = stepCount;
        this.dueDate = dueDate;
    }

    public boolean setStepData(int step, String title, String description){
        if (step > stepCount || step < 1)
            return false;

        stepTitles.set(step, title);
        stepDescriptions.set(step, description);
        stepStatus.set(step, false);
        return true;
    }

    public void addAttachment(File file){
        attachments.add(file);
    }

    public void addAttachments(List<File> files){
        attachments.addAll(files);
    }

    public boolean finishStep(int step){
        if(stepStatus.get(step))
            return false;
        stepStatus.set(step, true);
        if(step == stepCount)
            finishNote();
        return true;
    }

    private void finishNote(){
        noteStatus = true;
        //TODO move note to done Notes
    }

    public String getTitle() {
        return title;
    }

    public int getStepCount() {
        return stepCount;
    }

    public List<String> getStepTitles() {
        return stepTitles;
    }

    public List<String> getStepDescriptions() {
        return stepDescriptions;
    }

    public List<Boolean> getStepStatus() {
        return stepStatus;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public List<File> getAttachments() {
        return attachments;
    }

    public boolean isNoteStatus() {
        return noteStatus;
    }
}
