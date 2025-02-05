import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileHandler {

    private static final String DATA_DIR = "Development Task Jar.txt"; // Just the one txt file

    public static List<Task> readTasks() throws FileNotFoundException {
        List<Task> tasks = new ArrayList<>();
        Scanner scanner = new Scanner(new File(DATA_DIR));
                
        StringBuilder taskHeader = new StringBuilder();
        String date = null;
        String category = null;
        String dateCompleted = null;
        
        boolean readingMetadata = true; // Flag to track if we're expecting metadata
        
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
       
            // If empty line, reset state and prepare for next task
            if (line.trim().isEmpty()) {
                if (date != null) {
                    tasks.add(new Task(date, category, dateCompleted, taskHeader.toString().trim()));
                    taskHeader = new StringBuilder();
                    date = null;
                }
                readingMetadata = true; // Next non-empty line should be metadata
                continue;
            }
            
            // Process metadata line (only if we're expecting it)
            if (readingMetadata && line.contains("[")) {
                String[] parts = line.split("\\s+", 3);
                date = parts[0];
                category = parts[1].replaceAll("[\\[\\]]", "");
                dateCompleted = parts[2].equals("-") ? null : parts[2];
                readingMetadata = false; // Don't process any more metadata until next empty line
            } else {
                // It's a description line
                taskHeader.append(line).append("\n");
            }
        }
    
        // Add the last task if exists
        if (date != null) {
            tasks.add(new Task(date, category, dateCompleted, taskHeader.toString().trim()));
        }
        
        scanner.close();
        return tasks;
    }

    // This function currently overwrites all of the tasks. 
    public static void writeTasks(List<Task> tasks, String filePath) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (Task task : tasks) {
                String completionDate = task.getDateCompleted() == null ? "-" : task.getDateCompleted();
                writer.printf("%s [%s] %s%n", task.getDate(), task.getCategory(), completionDate);
                writer.println(task.getDescription());
                writer.println();  // Blank line between tasks
            }
        }
    }

    // Inserts new task at the top of the txt file. 
    public static void newTask() throws IOException {
        // Get the current date
        String date = new java.text.SimpleDateFormat("d-M-yyyy").format(new java.util.Date());
        // long unixTimestamp = System.currentTimeMillis();  // Unix TimeStamp to be used as primary identifier
        // Concatenate it onto " [] -"
        String taskHeader = new String(date + " [] -");
        System.out.println(taskHeader); // That line will then be followed by a new line.

        // Both of these will be inserted at the bottom of the txt file. 
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_DIR, true))) {
            writer.println(taskHeader);
            writer.println();  // Blank line between tasks
        }
    }
    
}
