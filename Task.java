import java.time.LocalDate;

/**
 * Task.java  -  the MODEL class.
 * One object of this class = one task (like one row in a table).
 */
public class Task {

    // ---------- Data (fields) ----------
    private int id;               // unique number of the task
    private String title;         // what has to be done
    private int priority;         // 1 = High, 2 = Medium, 3 = Low
    private LocalDate dueDate;    // last date to finish the task
    private boolean done;         // true = completed, false = pending

    // ---------- Constructor ----------
    public Task(int id, String title, int priority, LocalDate dueDate, boolean done) {
        this.id = id;
        this.title = title;
        this.priority = priority;
        this.dueDate = dueDate;
        this.done = done;
    }

    // ---------- Getters (read the private data) ----------
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getPriority() {
        return priority;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public boolean isDone() {
        return done;
    }

    // ---------- Setter (only 'done' needs to change later) ----------
    public void setDone(boolean done) {
        this.done = done;
    }

    // Converts the priority number into a readable word
    public String getPriorityName() {
        switch (priority) {
            case 1:
                return "High";
            case 2:
                return "Medium";
            default:
                return "Low";
        }
    }

    // ---------- File support ----------

    // Task object  ->  one line of text, e.g.  1|Study Java|1|2026-11-10|false
    public String toFileLine() {
        return id + "|" + title + "|" + priority + "|" + dueDate + "|" + done;
    }

    // One line of text  ->  Task object (the reverse of toFileLine)
    public static Task fromFileLine(String line) {
        String[] parts = line.split("\\|");          // cut the line at every '|'
        int id = Integer.parseInt(parts[0]);
        String title = parts[1];
        int priority = Integer.parseInt(parts[2]);
        LocalDate dueDate = LocalDate.parse(parts[3]);   // format yyyy-MM-dd
        boolean done = Boolean.parseBoolean(parts[4]);
        return new Task(id, title, priority, dueDate, done);
    }

    // How a task looks when printed on the screen
    @Override
    public String toString() {
        String status = done ? "Done" : "Pending";
        return String.format("%-4d %-28s %-8s %-12s %s",
                id, title, getPriorityName(), dueDate, status);
    }
}
