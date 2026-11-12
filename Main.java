import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main.java  -  the USER INTERFACE.
 * Shows the menu, takes input from the keyboard and calls TaskManager.
 */
public class Main {

    private static Scanner sc = new Scanner(System.in);
    private static TaskManager manager = new TaskManager();

    public static void main(String[] args) {
        System.out.println("===== DAILY TASK MANAGER =====");

        int choice = -1;
        while (choice != 0) {                 // keep showing the menu until user enters 0
            showMenu();
            choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    addTask();
                    break;
                case 2:
                    printTasks(manager.getAllTasks());
                    break;
                case 3:
                    printTasks(manager.getTodayTasks());
                    break;
                case 4:
                    printTasks(manager.getPendingTasks());
                    break;
                case 5:
                    markDone();
                    break;
                case 6:
                    deleteTask();
                    break;
                case 7:
                    searchTask();
                    break;
                case 8:
                    printTasks(manager.sortedByPriority());
                    break;
                case 9:
                    printTasks(manager.sortedByDueDate());
                    break;
                case 0:
                    System.out.println("Tasks saved. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter 0 to 9.");
            }
        }
    }

    // ---------- Menu ----------
    private static void showMenu() {
        System.out.println();
        System.out.println("1. Add task");
        System.out.println("2. View all tasks");
        System.out.println("3. View today's tasks");
        System.out.println("4. View pending tasks");
        System.out.println("5. Mark task as done");
        System.out.println("6. Delete task");
        System.out.println("7. Search task");
        System.out.println("8. Sort by priority");
        System.out.println("9. Sort by due date");
        System.out.println("0. Exit");
    }

    // ---------- Feature methods (ask the user, then call TaskManager) ----------
    private static void addTask() {
        System.out.print("Task title: ");
        String title = sc.nextLine().trim().replace("|", " ");   // '|' is our file separator
        if (title.isEmpty()) {
            System.out.println("Title cannot be empty.");
            return;
        }

        int priority = 0;
        while (priority < 1 || priority > 3) {
            priority = readInt("Priority (1 = High, 2 = Medium, 3 = Low): ");
        }

        LocalDate dueDate = readDate("Due date (yyyy-MM-dd) or press Enter for today: ");

        Task t = manager.addTask(title, priority, dueDate);
        System.out.println("Task added with ID " + t.getId());
    }

    private static void markDone() {
        int id = readInt("Enter task ID to mark as done: ");
        if (manager.markDone(id)) {
            System.out.println("Task marked as done.");
        } else {
            System.out.println("No task found with ID " + id);
        }
    }

    private static void deleteTask() {
        int id = readInt("Enter task ID to delete: ");
        if (manager.deleteTask(id)) {
            System.out.println("Task deleted.");
        } else {
            System.out.println("No task found with ID " + id);
        }
    }

    private static void searchTask() {
        System.out.print("Enter keyword: ");
        String keyword = sc.nextLine().trim();
        printTasks(manager.searchByKeyword(keyword));
    }

    // ---------- Helper methods ----------

    // Prints a list of tasks as a table
    private static void printTasks(ArrayList<Task> list) {
        if (list.isEmpty()) {
            System.out.println("No tasks to show.");
            return;
        }
        System.out.println();
        System.out.println(String.format("%-4s %-28s %-8s %-12s %s",
                "ID", "Title", "Priority", "Due Date", "Status"));
        System.out.println("-----------------------------------------------------------------");
        for (Task t : list) {
            System.out.println(t);
        }
    }

    // Keeps asking until the user types a valid whole number
    private static int readInt(String message) {
        while (true) {
            System.out.print(message);
            String text = sc.nextLine().trim();
            try {
                return Integer.parseInt(text);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    // Keeps asking until the user types a valid date (or presses Enter = today)
    private static LocalDate readDate(String message) {
        while (true) {
            System.out.print(message);
            String text = sc.nextLine().trim();
            if (text.isEmpty()) {
                return LocalDate.now();
            }
            try {
                return LocalDate.parse(text);
            } catch (DateTimeParseException e) {
                System.out.println("Wrong format. Example: 2026-11-10");
            }
        }
    }
}
