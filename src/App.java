import java.util.List;

public class App {

    public static final String DATA_DIR = "Development Task Jar.txt"; // Just the one txt file
    public static void main(String[] args) throws Exception {

        List<Task> tasks = FileHandler.readTasks(DATA_DIR);

        for (Task task : tasks) {
            System.out.println(task.toString());
            System.out.println();
        }
    }
}
