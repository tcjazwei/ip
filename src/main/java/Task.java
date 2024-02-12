public class Task {
    private final String taskName;
    private boolean isDone;

    /**
     * Constructor
     * 
     * @param name task name
     * @return new task instance
     */
    public Task(String name) {
        this.taskName = name;
        this.isDone = false;
    }

    /**
     * Marks task as done
     */
    public void mark() {
        this.isDone = true;
    }

    /**
     * Marks task as not done
     */
    public void unmark() {
        this.isDone = false;
    }

    /**
     * Returns a command that creates the task
     */
    public String getCommand() {
        String s = this.taskName;
        s += this.isDone ? " /" + Dackel.MARK : "";
        return s;
    }

    @Override
    public String toString() {
        String s = isDone ? "[X] " : "[ ] ";
        return s + this.taskName;
    }
}