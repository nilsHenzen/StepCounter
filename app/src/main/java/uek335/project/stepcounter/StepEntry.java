package uek335.project.stepcounter;
public class StepEntry {
    private final int count;
    private final String date;

    public StepEntry(int count, String date) {
        this.count = count;
        this.date = date;
    }

    public int getCount() {
        return count;
    }

    public String getDate() {
        return date;
    }
}
