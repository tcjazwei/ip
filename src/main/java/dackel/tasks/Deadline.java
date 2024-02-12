package dackel.tasks;

public class Deadline extends Task {
    private String dueTime;

    /**
     * Constructor
     * 
     * @param name task name
     * @param dueTime task due date/time as a String
     * @return new task instance
     */
    public Deadline(String name, String dueTime) {
        super(name);
        this.dueTime = dueTime;
    }

    /**
     * Returns a command that creates the task
     */
    @Override
    public String getCommand() {
        String s = super.getCommand();
        return "deadline " + s + " /" + Dackel.BY + " " + this.dueTime;
    }

    @Override
    public String toString() {
        String s = "[D]" + super.toString();
        return s + " (due by " + this.dueTime + ")";
    }
}