import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * FileHandler.java  -  reads tasks from a text file and writes them back.
 * This is how the data stays saved after the program is closed.
 */
public class FileHandler {

    private static final String FILE_NAME = "tasks.txt";

    // Read every line of tasks.txt and turn it into a Task object
    public static ArrayList<Task> load() {
        ArrayList<Task> list = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (!file.exists()) {          // first run: no file yet, start empty
            return list;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;                      // skip blank lines
                }
                try {
                    list.add(Task.fromFileLine(line));
                } catch (Exception e) {
                    System.out.println("Skipped a damaged line in " + FILE_NAME);
                }
            }
        } catch (IOException e) {
            System.out.println("Could not read file: " + e.getMessage());
        }
        return list;
    }

    // Write all tasks to tasks.txt, one task per line (old content is replaced)
    public static void save(ArrayList<Task> list) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Task t : list) {
                writer.println(t.toFileLine());
            }
        } catch (IOException e) {
            System.out.println("Could not save file: " + e.getMessage());
        }
    }
}
