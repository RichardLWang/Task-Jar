import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileHandler {

    public static List<Task> readTasks(String filePath) throws FileNotFoundException {
        List<Task> tasks = new ArrayList<>();
        Scanner scanner = new Scanner(new File(filePath));
            
        StringBuilder taskDescription = new StringBuilder();
        String date = null;
        String category = null;
        String dateCompleted = null;
        
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
   
            // If it's a blank line, finalize the current task
            if (line.trim().isEmpty()) {
                if (date != null) {
                    tasks.add(new Task(date, category, dateCompleted, taskDescription.toString().trim()));
                    taskDescription = new StringBuilder();
                    date = null;
                }
                continue;
            }
            
            // If line contains metadata (has square brackets)
            if (line.contains("[")) {
                String[] parts = line.split("\\s+", 3);  // Split into date, category, dateCompleted
                date = parts[0];
                category = parts[1].replaceAll("[\\[\\]]", "");  // Remove brackets
                dateCompleted = parts[2].equals("n/a") ? null : parts[2];
            } else {
                // It's a description line
                taskDescription.append(line).append("\n");
            }
        }

        // Add the last task if exists
        if (date != null) {
            tasks.add(new Task(date, category, dateCompleted, taskDescription.toString().trim()));
        }
        
        scanner.close();
        return tasks;
    }

    
}
