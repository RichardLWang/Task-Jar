import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.util.List;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.JLabel;


public class TaskListPanel extends JFrame {
    private List<Task> tasks;
    private int taskListPanelWidth = 720;
    
    // Takes a list of tasks and displays them 
    public TaskListPanel(List<Task> tasks) {
        this.tasks = tasks;

        // Frame setup
        this.setTitle("Task Jar");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        // this.setResizable(false);  // Prevents resizing
        this.setSize(taskListPanelWidth,900); // x & y dimension of frame/window 
        this.getContentPane().setBackground(new Color(255, 0, 255)); // Background colour
        this.setVisible(true);  // Makes a frame visible    

        
        // JAVA TRICK: the this keyword is not necessary as the methods are of the class.
        // The above would be referring to the current JFrame object.

        // Create main panel with vertical BoxLayout
        JPanel mainPanel = new JPanel();
        // Use vertical BoxLayout to stack tasks
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Add each task as a panel
        for (Task task : tasks) {
            JPanel taskPanel = createTaskPanel(task);
            // Make task panel take full width
            taskPanel.setMaximumSize(new Dimension(taskListPanelWidth - 60, taskPanel.getMaximumSize().height));
            mainPanel.add(taskPanel);
            mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }
        
        // Scroll capability: The tasks within the mainPanel will be put inside a scroll pane
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        // I only want to scroll vertically
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); 
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        scrollPane.getVerticalScrollBar().setUnitIncrement(15); // Scroll Speed

        // Add scroll pane to frame
        this.add(scrollPane);
        
    
    }

    private JPanel createTaskPanel(Task task) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));  // Visible border for the tasks
        

        // METADATA PANEL (date, category, completion date)
        JPanel metadataPanel = new JPanel();
        metadataPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        // Format the metadata string
        String completionDate = (task.getDateCompleted() == null) ? "n/a" : task.getDateCompleted();
        String metadata = String.format("%s [%s] %s", task.getDate(), task.getCategory(), completionDate);
        JLabel metadataLabel = new JLabel(metadata);
        metadataLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Font name, style, size
        metadataPanel.add(metadataLabel);
        

        // DESCRIPTION PANEL
        JPanel descriptionPanel = new JPanel();
        descriptionPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        // Add description with word wrap
        JTextArea descriptionArea = new JTextArea(task.getDescription());
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setLineWrap(true);
        descriptionArea.setEditable(false);
        descriptionArea.setBackground(panel.getBackground());

        // TASK PANEL SIZE
        descriptionArea.setPreferredSize(new Dimension(taskListPanelWidth - 80, // The panel height shuold be dictated by the size of the description.
            (task.getDescription().length() > 100) ? (task.getDescription().length())/3 : 25)); 
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 16));    
        descriptionPanel.add(descriptionArea);
        
        // Add both panels
        panel.add(metadataPanel);
        panel.add(descriptionPanel);

        return panel;
    }

}
