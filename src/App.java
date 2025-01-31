import java.util.List;

import javax.swing.SwingUtilities;

public class App {

    public static final String DATA_DIR = "Development Task Jar.txt"; // Just the one txt file
    public static void main(String[] args) throws Exception {

        List<Task> tasks = FileHandler.readTasks(DATA_DIR);

        new TaskListPanel(tasks).setVisible(true);  

    }
}
