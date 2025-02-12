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

    // Why are these class variables instead of methods. So they can be accessed by other methods.
    private JPanel listButtonPanel;
    private JPanel taskButtonPanel;  // The taskButton panel containing the save button will brought up when a task is clicked on to be edited.

    private Task currentEditingTask = null;  // Keep track of which task is being edited
    private JTextArea currentEditingArea = null;  // Keep track of which text area is being edited

    
    // Takes a list of tasks and displays them 
    public TaskListPanel(List<Task> tasks) {
        taskModel = new TaskListModel(tasks);
        taskModel.toggleFIFOLIFO(); // Set the order of the tasks within the constructor

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

        listButtonPanel = createListButtonPanel();

        displayTasks();
        
        this.add(createScrollPane(mainPanel)); // The default is already the centre of the border layout (scrollPane, BorderLayout.CENTER)?
        this.add(listButtonPanel, BorderLayout.SOUTH);  // Button Panel goes on the south border.      
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
        // Change panel colour to indicate completion
        if (task.getDateCompleted() != null) {
            metadataPanel.setBackground(Color.magenta);  
        }

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

    private JPanel createListButtonPanel() {
        JPanel listButtonPanel = new JPanel(); 
        listButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 0)); // Use FlowLayout's gap parameter. Component orientation (The components are added from the right)
        listButtonPanel.setBackground(Color.green);
        listButtonPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Consistent padding
        listButtonPanel.add(createNewTaskButton());
        listButtonPanel.add(createTaskOrderToggleButton());
        return listButtonPanel;
    }

    private JPanel createTaskButtonPanel() {
        JPanel taskButtonPanel = new JPanel(); 
        taskButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 0)); // Use FlowLayout's gap parameter. Component orientation (The components are added from the right)
        taskButtonPanel.setBackground(Color.yellow);
        taskButtonPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Consistent padding
        taskButtonPanel.add(createSaveButton());
        return taskButtonPanel;
    }

    private JButton createTaskOrderToggleButton() {
        JButton taskOrderToggle = new JButton();
        taskOrderToggle.setText("FIFO/LIFO");
        taskOrderToggle.setFocusable(false); 
        taskOrderToggle.setFont(new Font("Comic Sans",Font.BOLD,25));
        taskOrderToggle.setForeground(Color.green);
		// taskOrderToggle.setBounds(500, 500, 10, 10);  

        // Use a lambda instead of implementing Action Listener
        taskOrderToggle.addActionListener(e -> {
            taskModel.toggleFIFOLIFO();
            displayTasks();
                            // // Refresh display
                            // mainPanel.revalidate();
                            // mainPanel.repaint();
        });
        // taskOrderToggle.setText(taskModel.isFIFO() ? "LIFO" : "FIFO");
        return taskOrderToggle;
    }

    private JButton createNewTaskButton() {
        JButton newTaskButton = new JButton("New Task");
        newTaskButton.setFocusable(false); 
        newTaskButton.setFont(new Font("Comic Sans", Font.BOLD, 25));
        newTaskButton.setForeground(Color.magenta);
        newTaskButton.addActionListener(e -> {
                Task newTask = new Task(new java.text.SimpleDateFormat("d-M-yyyy").format(new java.util.Date()), "Coding", null, "");
                // Create panel for new task
                JPanel taskPanel = createTaskPanel(newTask);
                taskPanel.setMaximumSize(new Dimension(frameWidth - 60, 
                    taskPanel.getMaximumSize().height));

                // Add to top of mainPanel
                mainPanel.add(taskPanel, 0);
                mainPanel.add(Box.createRigidArea(new Dimension(0, 10)), 1); 
                
                taskModel.addTask(newTask);
                
                // Refresh display
                mainPanel.revalidate();
                mainPanel.repaint();
        });
        return newTaskButton;
    }
    
    private JButton createSaveButton() {
        JButton saveButton = new JButton("Task Save");
        saveButton.setFocusable(false); 
        saveButton.setFont(new Font("Comic Sans", Font.BOLD, 25));
        saveButton.setForeground(Color.red);
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

        this.remove(listButtonPanel);
        taskButtonPanel = createTaskButtonPanel();
        this.add(taskButtonPanel, BorderLayout.SOUTH);
        this.revalidate();
        this.repaint();
    }

    private void saveTaskChanges() {
        if (currentEditingTask != null && currentEditingArea != null) {  // Check state of any edits in progress
            // Update the task's description by retrieving text from the editing area
            String newDescription = currentEditingArea.getText();
            currentEditingTask.setDescription(newDescription);  // You'll need to add this method to Task class
            
            // Update the file
            try {
                FileHandler.writeTasks(taskModel.getTasks());
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

            // Remove task panel and restore list panel
            this.remove(taskButtonPanel);
            this.add(listButtonPanel, BorderLayout.SOUTH);
            this.revalidate();
            this.repaint();

            taskButtonPanel = null; // Clear the reference
            currentEditingTask = null;
            currentEditingArea = null;
        }
    }

}