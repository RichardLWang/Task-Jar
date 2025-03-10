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

    public String getDate() {return date;}
 
    public String getCategory() {return category;}
 
    public String getDateCompleted() {return dateCompleted;}
 
    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}

    public void setDateCompleted(String dateCompleted) {this.dateCompleted = dateCompleted;}
    
    public String toString() {
        String completionDate = (dateCompleted == null) ? "NOT COMPLETE" : dateCompleted;
        return String.format("%s [%s] %s%n%s", 
            date, 
            category, 
            completionDate,
            description);
    }


}