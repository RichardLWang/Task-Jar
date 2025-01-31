import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TaskListModel {  // The TaskListModel contains the methods to manipulate the tasks
     private List<Task> tasks;
     private boolean isFIFO = true;  // true for FIFO, false for LIFO
     
     public TaskListModel(List<Task> tasks) {
          this.tasks = new ArrayList<>(tasks); // Create a copy of the list
     }
    
     public List<Task> getTasks() {
          return new ArrayList<>(tasks); // Return a copy to prevent external modification
     }

     private String reverseDateFormat(String date) {
          String[] parts = date.split("-");
          String day = parts[0].length() == 1 ? "0" + parts[0] : parts[0];  // Puts a 0 in front if a singular date digit is in front
          String month = parts[1].length() == 1 ? "0" + parts[1] : parts[1];
          return parts[2] + month + day;
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
          
          // Sorts the incompleteTasks by date
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