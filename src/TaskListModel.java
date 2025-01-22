import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TaskListModel {
     private List<Task> tasks;
     private boolean isFIFO = true;  // true for FIFO, false for LIFO
     
     public TaskListModel(List<Task> tasks) {
          this.tasks = new ArrayList<>(tasks); // Create a copy of the list
     }
    
     // Get current tasks
     public List<Task> getTasks() {
          return new ArrayList<>(tasks); // Return a copy to prevent external modification
     }

     private String reverseDateFormat(String date) {
          // Remove dashes and reverse from DD-MM-YYYY to YYYYMMDD
          String[] parts = date.split("-");
          return parts[2] + parts[1] + parts[0];
     }

     public void toggleFIFOLIFO() {
          // First, separate incomplete and complete tasks
          List<Task> incompleteTasks = new ArrayList<>();
          List<Task> completedTasks = new ArrayList<>();
          
          for (Task task : tasks) {
               if (task.getDateCompleted() == null) {
                    incompleteTasks.add(task);
               } else {
                    completedTasks.add(task);
               }
          }
          
          Collections.sort(incompleteTasks, (task1, task2) -> {
               String date1 = reverseDateFormat(task1.getDate());
               String date2 = reverseDateFormat(task2.getDate());
               return date1.compareTo(date2);
          });
          
          // Reverse if LIFO
          if (!isFIFO) {
               Collections.reverse(incompleteTasks);
          }
          
          // Combine lists with incomplete tasks at top
          tasks.clear();
          tasks.addAll(incompleteTasks);
          tasks.addAll(completedTasks);
          
          isFIFO = !isFIFO;
     }
 
     public boolean isFIFO() {
          return isFIFO;
     }

}