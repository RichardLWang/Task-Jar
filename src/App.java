import java.util.List;

import javax.swing.SwingUtilities;

public class App {

    public static final String DATA_DIR = "Development Task Jar.txt"; // Just the one txt file
    public static void main(String[] args) throws Exception {

        List<Task> tasks = FileHandler.readTasks(DATA_DIR);

        // SwingUtilities.invokeLater(() -> {
        //         TaskListPanel gui = new TaskListPanel(tasks);
        //         gui.setVisible(true);
        // });
        new TaskListPanel(tasks).setVisible(true);  

        

        for (Task task : tasks) {
            System.out.println(task.toString());
            System.out.println();
        }

        // new TaskListPanel(tasks);

    }
}
