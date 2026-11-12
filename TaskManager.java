import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * TaskManager.java  -  the LOGIC class.
 * Holds the list of tasks and has one method for every feature.
 * It never prints a menu and never reads the keyboard - that is Main's job.
 */
public class TaskManager {

    private ArrayList<Task> tasks;   // all tasks live here while the program runs
    private int nextId;              // id to give to the next new task

    // Constructor: load saved tasks and work out the next free id
    public TaskManager() {
        tasks = FileHandler.load();
        nextId = 1;
        for (Task t : tasks) {                 // find the biggest id used so far
            if (t.getId() >= nextId) {
                nextId = t.getId() + 1;
            }
        }
    }

    // ---------- 1. ADD ----------
    public Task addTask(String title, int priority, LocalDate dueDate) {
        Task t = new Task(nextId, title, priority, dueDate, false);
        nextId++;
        tasks.add(t);
        save();
        return t;
    }

    // ---------- Linear search by id ----------
    // Check tasks one by one from the start until the id matches.
    public Task findById(int id) {
        for (Task t : tasks) {
            if (t.getId() == id) {
                return t;
            }
        }
        return null;     // not found
    }

    // ---------- 2. VIEW ----------
    public ArrayList<Task> getAllTasks() {
        return tasks;
    }

    public ArrayList<Task> getPendingTasks() {
        ArrayList<Task> result = new ArrayList<>();
        for (Task t : tasks) {
            if (!t.isDone()) {
                result.add(t);
            }
        }
        return result;
    }

    public ArrayList<Task> getTodayTasks() {
        ArrayList<Task> result = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (Task t : tasks) {
            if (t.getDueDate().equals(today)) {
                result.add(t);
            }
        }
        return result;
    }

    // ---------- 3. MARK AS DONE ----------
    public boolean markDone(int id) {
        Task t = findById(id);
        if (t == null) {
            return false;
        }
        t.setDone(true);
        save();
        return true;
    }

    // ---------- 4. DELETE ----------
    public boolean deleteTask(int id) {
        Task t = findById(id);
        if (t == null) {
            return false;
        }
        tasks.remove(t);
        save();
        return true;
    }

    // ---------- 5. SEARCH by keyword ----------
    public ArrayList<Task> searchByKeyword(String keyword) {
        ArrayList<Task> result = new ArrayList<>();
        String key = keyword.toLowerCase();
        for (Task t : tasks) {
            if (t.getTitle().toLowerCase().contains(key)) {
                result.add(t);
            }
        }
        return result;
    }

    // ---------- 6. SORT ----------
    // Both methods sort a COPY, so the saved order is not disturbed.

    // High priority first; if priority is same, earlier due date first
    public ArrayList<Task> sortedByPriority() {
        ArrayList<Task> copy = new ArrayList<>(tasks);
        copy.sort(Comparator.comparingInt(Task::getPriority)
                .thenComparing(Task::getDueDate));
        return copy;
    }

    // Earlier due date first
    public ArrayList<Task> sortedByDueDate() {
        ArrayList<Task> copy = new ArrayList<>(tasks);
        copy.sort(Comparator.comparing(Task::getDueDate));
        return copy;
    }

    // ---------- Save ----------
    private void save() {
        FileHandler.save(tasks);
    }
}
