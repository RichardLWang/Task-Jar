import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.util.Arrays;
import java.util.List;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

// The TaskListPanel is the Graphical User Interface
public class TaskListPanel extends JFrame {

    private TaskListModel taskModel;
    private JPanel mainPanel;
    private int frameWidth = 720;
    private int frameHeight = 900;

    private JPanel buttonPanel;
    private JButton saveButton;  // The save button that will appear when editing

    private Task currentEditingTask = null;  // Keep track of which task is being edited
    private JTextArea currentEditingArea = null;  // Keep track of which text area is being edited

    
    // Takes a list of tasks and displays them 
    public TaskListPanel(List<Task> tasks) {
        taskModel = new TaskListModel(tasks);

        // Frame setup
        this.setTitle("Task Jar");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        // this.setResizable(false);  // Prevents resizing
        this.setSize(frameWidth, frameHeight); // x & y dimension of frame/window 
        this.getContentPane().setBackground(Color.MAGENTA); // Background colour
        this.setVisible(true);  // Makes a frame visible    
        this.setLayout(new BorderLayout());
                
        // JAVA TRICK: the this keyword is not necessary as the methods are of the class.
        // The above would be referring to the current JFrame object.

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); // Use vertical BoxLayout to stack tasks

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Buttons added to the left
        buttonPanel.add(createTaskOrderToggleButton());
        saveButton = createSaveButton();

        displayTasks();
        
        this.add(createScrollPane(mainPanel)); // The default is already the centre of the border layout (scrollPane, BorderLayout.CENTER)?
        this.add(buttonPanel, BorderLayout.SOUTH);  // Button Panel goes on the south border.         
    }




    // FUNCTIONS

    private void displayTasks() {
        mainPanel.removeAll();
        for (Task task : taskModel.getTasks()) {
            JPanel taskPanel = createTaskPanel(task);
            taskPanel.setMaximumSize(new Dimension(frameWidth - 60, taskPanel.getMaximumSize().height));
            mainPanel.add(taskPanel);
            mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }
        
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private JPanel createTaskPanel(Task task) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));  // Visible border for the tasks
        

        // Metadata PANEL (date, category, completion date)
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
        descriptionArea.setWrapStyleWord(true);  // Ensures wrapping occurs at word boundaries, preventing words from being split.
        descriptionArea.setLineWrap(true);  // Enables line wrapping so text doesn't extend beyond the JTextArea width.
        descriptionArea.setEditable(false);
        descriptionArea.setBackground(panel.getBackground());

        // TASK PANEL SIZE
        descriptionArea.setPreferredSize(new Dimension(frameWidth - 80, // The panel height shuold be dictated by the size of the description.
            (task.getDescription().length() > 100) ? (task.getDescription().length())/2 : 100)); 
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 16));    
        descriptionPanel.add(descriptionArea);
        
        // Add click listener to description area
        descriptionArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!descriptionArea.isEditable()) {    // If the descriptionArea is not in editing mode.
                    startEditing(task, descriptionArea);
                }
            }
        });

        // Add both panels
        panel.add(metadataPanel);
        panel.add(descriptionPanel);

        return panel;
    }

    private JScrollPane createScrollPane(JPanel scrollablePanel) {
        // Scroll capability: The tasks within the mainPanel will be put inside a scroll pane
        JScrollPane scrollPane = new JScrollPane(scrollablePanel);
        // I only want to scroll vertically
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); 
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        scrollPane.getVerticalScrollBar().setUnitIncrement(15); // Scroll Speed

        return scrollPane;
    }

    private JPanel createTaskOrderToggleButton() {
        JButton taskOrderToggle = new JButton();
		// taskOrderToggle.setBounds(500, 500, 10, 10);  

        // Use a lambda instead of implementing Action Listener
        taskOrderToggle.addActionListener(e -> {
            taskModel.toggleFIFOLIFO();
            displayTasks();
        });

        // taskOrderToggle.setText(taskModel.isFIFO() ? "LIFO" : "FIFO");
        taskOrderToggle.setText("FIFO/LIFO");
        taskOrderToggle.setFocusable(false); 
        taskOrderToggle.setFont(new Font("Comic Sans",Font.BOLD,25));
        taskOrderToggle.setForeground(Color.green);

        // Panel to hold taskOrderToggle button
        JPanel buttonPanel = new JPanel(); 
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0)); // component orientation, horizontal gap, vertical gap
        buttonPanel.setBackground(Color.green);

        // Add some padding/margin on the right
        buttonPanel.add(Box.createHorizontalStrut(10));  // Left margin
        buttonPanel.add(taskOrderToggle);
        buttonPanel.add(Box.createHorizontalStrut(10));  // Right margin
        // Optional: add some vertical padding
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));  // top, left, bottom, right

        return buttonPanel;
    }

    private JButton createSaveButton() {
        JButton saveButton = new JButton("Task Save");
        saveButton.setFocusable(false); 
        saveButton.setFont(new Font("Comic Sans", Font.BOLD, 25));
        saveButton.setForeground(Color.red);

        buttonPanel.setBackground(Color.green);

        saveButton.addActionListener(e -> saveTaskChanges());
        return saveButton;
    }

    
    private void startEditing(Task task, JTextArea area) {
        // If already editing something else, save it first
        if (currentEditingTask != null) {
            saveTaskChanges();  // If you click into another descriptionArea the changes for the task will be saved.
        }
        
        // Set up new editing session
        currentEditingTask = task;
        currentEditingArea = area;
        area.setEditable(true);
        area.setBackground(new Color(255, 255, 200));  // Light yellow to indicate editing
        
        // Add save button if not already there
        if (!Arrays.asList(buttonPanel.getComponents()).contains(saveButton)) {
            buttonPanel.add(saveButton);
            buttonPanel.revalidate();
            buttonPanel.repaint();
        }
    }

    private void saveTaskChanges() {
        if (currentEditingTask != null && currentEditingArea != null) {  // Check state of any edits in progress
            // Update the task's description by retrieving text from the editing area
            String newDescription = currentEditingArea.getText();
            currentEditingTask.setDescription(newDescription);  // You'll need to add this method to Task class
            
            // Update the file
            try {
                FileHandler.writeTasks(taskModel.getTasks(), "Development Task Jar.txt");
            } catch (IOException ex) {
                // Show error message to user
                JOptionPane.showMessageDialog(this, 
                    "Error saving changes: " + ex.getMessage(), 
                    "Save Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
            
            // Reset editing state
            currentEditingArea.setEditable(false);
            currentEditingArea.setBackground(null);  // Reset background color
            buttonPanel.remove(saveButton);  // Remove button after saving
            buttonPanel.revalidate();
            buttonPanel.repaint();
            
            currentEditingTask = null;
            currentEditingArea = null;
        }
    }

}
