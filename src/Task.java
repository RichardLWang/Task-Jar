public class Task {
    private String date;
    private String category;
    private String dateCompleted;
    private String description;
    
    public Task(String date, String category, String dateCompleted, String description) {
        this.date = date;
        this.category = category;
        this.dateCompleted = dateCompleted;
        this.description = description;
    }

    
    public String toString() {
        String completionDate = (dateCompleted == null) ? "n/a" : dateCompleted;
        return String.format("%s [%s] %s%n%s", 
            date, 
            category, 
            completionDate,
            description);
    }


}
