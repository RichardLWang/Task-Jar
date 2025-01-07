import java.awt.Color;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;


public class TaskListPanel extends JFrame {
    private List<Task> tasks;
    
    // Takes a list of tasks and displays them 
    public TaskListPanel(List<Task> tasks) {
        this.tasks = tasks;

        // Frame setup
        this.setTitle("Task Jar");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        // this.setResizable(false);  // Prevents resizing
        this.setSize(720,900); // x & y dimension of frame    
        this.setVisible(true);  // Makes a frame visible    
        this.getContentPane().setBackground(new Color(255, 0, 255)); // Background colour
    
        JPanel firstTaskPanel = new JPanel();


        JLabel firstTask = new JLabel(tasks.get(0).toString());

        firstTaskPanel.add(firstTask);

        this.add(firstTaskPanel);



        JPanel secondTaskPanel = new JPanel();


        JLabel secondTask = new JLabel(tasks.get(1).toString());

        secondTaskPanel.add(secondTask);

        this.add(secondTaskPanel);


        
        
        
    
    }

}
