package dackel.tasks;

public class Event extends Task {
    private String startTime;
    private String endTime;

    /**
     * Constructor
     * 
     * @param name task name
     * @param startTime starting date/time of the task as a String
     * @param endTime ending date/time of the task as a String
     * @return new task instance
     */
    public Event(String name, String startTime, String endTime) {
        super(name);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Returns a command that creates the task
     */
    @Override
    public String getCommand() {
        String s = super.getCommand();
        return "event " + s + " /" + Dackel.FROM + " " + this.startTime + " /" + Dackel.UNTIL + " " + this.endTime;
    }

    @Override
    public String toString() {
        String s = "[E]" + super.toString();
        s = s + " (from " + this.startTime + " ";
        return s + "until " + this.endTime + ")";
    }
}