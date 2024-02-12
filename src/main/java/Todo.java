class Todo extends Task {
    /**
     * Constructor
     * 
     * @param name task name
     * @return new task instance
     */
    public Todo(String name) {
        super(name);
    }

    /**
     * Returns a command that creates the task
     */
    @Override
    public String getCommand() {
        String s = super.getCommand();
        return "todo " + s;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}