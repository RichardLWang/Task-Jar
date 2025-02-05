import java.util.List;
public class App {
    public static void main(String[] args) throws Exception {
        List<Task> tasks = FileHandler.readTasks();
        new TaskListPanel(tasks).setVisible(true);  
    }
}