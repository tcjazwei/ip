package dackel.app;

import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.IOException;

import dackel.tasks.Task;
import dackel.tasks.Todo;
import dackel.tasks.Deadline;
import dackel.tasks.Event;

public class Dackel {
    /** Command aliases */
    public static final String QUIT = "bye";
    public static final String LIST = "list";
    public static final String MARK = "mark";
    public static final String UNMARK = "unmark";
    public static final String TODO = "todo";
    public static final String DEADLINE = "deadline";
    public static final String EVENT = "event";
    public static final String DELETE = "delete";

    /** Command flags */
    public static final String COMMAND = "command";
    public static final String BODY = "///";
    public static final String BY = "by";
    public static final String FROM = "from";
    public static final String UNTIL = "to";

    /** File paths */
    private static final String DATA_PATH = "data.txt";

    /** Other String constants */
    private static final String NAME = "DACKEL";
    private static final String LOGO = " ____    __    ___  _  _  ____  __   \n"
        + "(  _ \\  /__\\  / __)( )/ )( ___)(  )  \n"
        + " )(_) )/(__)\\( (__  )  (  )__)  )(__ \n"
        + "(____/(__)(__)\\___)(_)\\_)(____)(____) ";
    private static final String LINE = "____________________________________________________________";
    private static final String VERSION = "Version 0.1";

    /** Scanner for receiving user input*/
    private static final Scanner SCANNER = new Scanner(System.in);

    /** File IO classes */
    private static BufferedReader reader = null;
    private static PrintWriter writer = null;

    /** Memory for storing task and instruction data */
    private static ArrayList<Task> storedTasks = new ArrayList<>();
    private static int numberOfTasks = 0;
    private static HashMap<String, String> commandArgs = new HashMap<>();

    /**
     * Simulates Dackel speaking to the user ala a chatroom interface
     * 
     * @param string Message for Dackel to send
     */
    private static void speak(String message) {
        // TODO: make this take in String[] args
        System.out.println(NAME + ": " + message);
    }

    // TODO: perhaps collapse the three add task methods into a single method

    /**
     * Adds a Todo to storedTasks
     * 
     * @param taskName name of task to be added
     * @param isMarked true if task is marked as done
     * @param isSilent true if Dackel should not speak when addTodo is executed
     */
    private static void addTodo(String taskName, boolean isMarked, boolean isSilent) {
        Todo newTask = new Todo(taskName);
        if (isMarked) {
            newTask.mark();
        }
        storedTasks.add(newTask);
        numberOfTasks++;
        if (isSilent) {
            return;
        }
        speak("added the following task to your list!\n " + newTask.toString());
        speak("your list now has " + String.valueOf(numberOfTasks) + " tasks.");
    }

    /**
     * Adds a Todo to storedTasks
     * 
     * @param taskName name of task to be added
     */
    private static void addTodo(String taskName) {
        addTodo(taskName, false, false);
    }

    /**
     * Adds a Deadline to storedTasks
     * 
     * @param taskName name of task to be added
     * @param dueTime due date/time of task as a String
     * @param isMarked true if task is marked as done
     * @param isSilent true if Dackel should not speak when addTodo is executed
     */
    private static void addDeadline(String taskName, String dueTime, boolean isMarked, boolean isSilent) {
        Deadline newTask = new Deadline(taskName, dueTime);
        if (isMarked) {
            newTask.mark();
        }
        storedTasks.add(newTask);
        numberOfTasks++;
        if (isSilent) {
            return;
        }
        speak("added the following task to your list!\n " + newTask.toString());
        speak("your list now has " + String.valueOf(numberOfTasks) + " tasks.");
    }

    /**
     * Adds a Deadline to storedTasks
     * 
     * @param taskName name of task to be added
     * @param dueTime due date/time of task as a String
     */
    private static void addDeadline(String taskName, String dueTime) {
        addDeadline(taskName, dueTime, false, false);
    }

    /**
     * Adds an Event to storedTasks
     * 
     * @param taskName name of task to be added
     * @param startTime starting date/time of task as a String
     * @param endTime ending date/time of task as a String
     * @param isMarked true if task is marked as done
     * @param isSilent true if Dackel should not speak when addTodo is executed
     */
    private static void addEvent(String taskName, String startTime, String endTime, boolean isMarked, boolean isSilent) {
        Event newTask = new Event(taskName, startTime, endTime);
        if (isMarked) {
            newTask.mark();
        }
        storedTasks.add(newTask);
        numberOfTasks++;
        if (isSilent) {
            return;
        }
        speak("added the following task to your list!\n " + newTask.toString());
        speak("your list now has " + String.valueOf(numberOfTasks) + " tasks.");
    }

    /**
     * Adds an Event to storedTasks
     * 
     * @param taskName name of task to be added
     * @param startTime starting date/time of task as a String
     * @param endTime ending date/time of task as a String
     */
    private static void addEvent(String taskName, String startTime, String endTime) {
        addEvent(taskName, startTime, endTime, false, false);
    }

    /**
     * Removes task with specified index from storedTasks
     * 
     * @param index index of the element to be removed
     */
    private static void deleteTask(int index) {
        Task taskToBeRemoved = storedTasks.get(index);
        String s = "the following task will be deleted from your list:\n ";
        s += taskToBeRemoved.toString();
        speak(s);
        speak("are you sure you want to do this? [Y/n]: ");
        String confirmation = receiveInput();
        if (confirmation.equals("Y")) {
            storedTasks.remove(index);
            numberOfTasks--;
            speak("task " + taskToBeRemoved.toString() + " successfully deleted.");
            speak("you now have " + String.valueOf(numberOfTasks) + " tasks on your list.");
        }
        else {
            speak("task was not deleted.");
        }
    }

    /**
     * Lists all tasks in storedTasks. Displays a message if storedTasks is empty
     */
    private static void listTasks() {
        if (numberOfTasks == 0) {
            speak("you have no tasks on your list!");
            return;
        }
        String s = "";
        for (int i = 0; i < numberOfTasks; i++) {
            s += "\n" + String.format(" %2d " + storedTasks.get(i).toString(), i + 1);
        }
        speak(s);
    }

    /**
     * Marks the specified task as done
     * 
     * @param index index of task in storedTasks
     */
    private static void markTask(int index) {
        storedTasks.get(index).mark();
        speak("i've marked the following task as done!\n " + storedTasks.get(index).toString());
    }

    /**
     * Marks the specified task as not done
     * 
     * @param index index of task in storedTasks
     */
    private static void unmarkTask(int index) {
        storedTasks.get(index).unmark();
        speak("i've unmarked the following task. do it soon, please!\n " + storedTasks.get(index).toString());
    }

    /**
     * Handles quitting, alongside errors that may arise during quitting
     * @return false if Dackel should continue to quit, true otherwise 
     */
    private static boolean quit() {
        try {
            // save current tasks
            writeTasksToFile();
            speak("your tasks have been saved successfully!");
            return false;
        }
        catch (IOException e) {
            speak("an error occured while trying to save your list!");
            speak("if i continue to shut down, your current tasks may be lost forever!");
            speak("would you still like to quit anyway? [Y/n]:");
            String confirmation = receiveInput();
            if (confirmation.equals("Y")) {
                return false;
            }
            else {
                return true;
            }
        }
    }

    /**
     * Prompts user for input and returns it
     * 
     * @return User-inputted string
     */
    private static String receiveInput() {
        System.out.print("> ");
        String input = SCANNER.nextLine();
        return input;
    }

    /**
     * Parses the arguments of the user input and separates them into elements of a String array
     * 
     * @param input user input as a single String
     * @return void, but sets commandArgs based on the input. 
     *     commandArgs[COMMAND] is the command, 
     *     commandArgs[BODY] is the unflagged part of the user input,
     *     subsequent flagged elements are stored as commandArgs[flag name].
     */
    private static void parseInput(String input) {
        commandArgs.clear();
        String[] splittedInput = input.split(" ");
        commandArgs.put(COMMAND, splittedInput[0]);
        String currentFlag = BODY;
        for (int i = 1; i < splittedInput.length; i++) {
            String currentString = splittedInput[i];
            if (currentString.charAt(0) == '/') {
                currentFlag = currentString.substring(1);
                commandArgs.put(currentFlag, null);
                continue;
            }
            if (commandArgs.get(currentFlag) == null) {
                commandArgs.put(currentFlag, currentString);
            }
            else {
                commandArgs.put(currentFlag, commandArgs.get(currentFlag) + " " + currentString);
            }
        }
    }

    /**
     * Executes a command based on the input String. Also does most error handling
     * 
     * @param input user input String
     * @return boolean representing if Dackel is to terminate after executeInput returns
     */
    // TODO: make error handling more specific: say which flags are there when they shouldn't, e.g.
    private static boolean executeInput(String input) throws DackelException {
        parseInput(input);
        String command = commandArgs.get(COMMAND);
        String body = commandArgs.get(BODY);
        switch (command) {
        case QUIT:
            if (commandArgs.size() > 1) {
                throw new DackelException("too many arguments!");
            }
            return quit();
        case LIST:
            if (commandArgs.size() > 1) {
                throw new DackelException("too many arguments!");
            }
            listTasks();
            break;
        case MARK:
            if (body == null) {
                throw new DackelException("list index cannot be left blank!");
            }
            if (body.length() == 0) {
                throw new DackelException("list index cannot be left blank!");
            }
            if (commandArgs.size() > 2) {
                throw new DackelException("too many arguments!");
            }
            try {
                int index = Integer.valueOf(body) - 1;
                if (index >= numberOfTasks) {
                    throw new DackelException("there is no task with that index in the list.");
                }
                if (index < 0) {
                    throw new DackelException("list indices must be greater than 0!");
                }
                markTask(index);
            }
            catch (NumberFormatException e) {
                throw new DackelException("\"" + body + "\" isn't a number!");
            }
            break;
        case UNMARK:
            if (body == null) {
                throw new DackelException("list index cannot be left blank!");
            }
            if (body.length() == 0) {
                throw new DackelException("list index cannot be left blank!");
            }
            if (commandArgs.size() > 2) {
                throw new DackelException("too many arguments!");
            }
            try {
                int index = Integer.valueOf(body) - 1;
                if (index >= numberOfTasks) {
                    throw new DackelException("there is no task with that index in the list.");
                }
                if (index < 0) {
                    throw new DackelException("list indices must be greater than 0!");
                }
                unmarkTask(index);
            }
            catch (NumberFormatException e) {
                throw new DackelException("\"" + body + "\" isn't a number!");
            }
            break;
        case TODO:
            if (body == null) {
                throw new DackelException("task description can't be left empty!");
            }
            if (body.length() == 0) {
                throw new DackelException("task description can't be left empty!");
            }
            if (commandArgs.size() > 2) {
                throw new DackelException("too many arguments!");
            }
            addTodo(body);
            break;
        case DEADLINE:
            String by = commandArgs.get(BY);
            if (body == null) {
                throw new DackelException("task description can't be left empty!");
            }
            if (body.length() == 0) {
                throw new DackelException("task description can't be left empty!");
            }
            if (by == null) {
                throw new DackelException("due date/time must be specified! use the /" + BY + " flag.");
            }
            if (by.length() == 0) {
                throw new DackelException("task due date/time can't be left empty!");
            }
            if (commandArgs.size() > 3) {
                throw new DackelException("too many arguments!");
            }
            addDeadline(body, by);
            break;
        case EVENT:
            String from = commandArgs.get(FROM);
            String until = commandArgs.get(UNTIL);
            if (body == null) {
                throw new DackelException("task description can't be left empty!");
            }
            if (body.length() == 0) {
                throw new DackelException("task description can't be left empty!");
            }
            if (from == null) {
                throw new DackelException("starting date/time must be specified! use the /" + FROM + " flag.");
            }
            if (from.length() == 0) {
                throw new DackelException("task starting date/time can't be left empty!");
            }
            if (until == null) {
                throw new DackelException("ending date/time must be specified! use the /" + UNTIL + " flag.");
            }
            if (until.length() == 0) {
                throw new DackelException("task ending date/time can't be left empty!");
            }
            if (commandArgs.size() > 4) {
                throw new DackelException("too many arguments!");
            }
            addEvent(body, from, until);
            break;
        case DELETE:
            if (body == null) {
                throw new DackelException("list index cannot be left blank!");
            }
            if (body.length() == 0) {
                throw new DackelException("list index cannot be left blank!");
            }
            if (commandArgs.size() > 2) {
                throw new DackelException("too many arguments!");
            }
            try {
                int index = Integer.valueOf(body) - 1;
                if (index >= numberOfTasks) {
                    throw new DackelException("there is no task with that index in the list.");
                }
                if (index < 0) {
                    throw new DackelException("list indices must be greater than 0!");
                }
                deleteTask(index);
            }
            catch (NumberFormatException e) {
                throw new DackelException("\"" + body + "\" isn't a number!");
            }
            break;
        default:
            if (command.length() == 0) {
                throw new DackelException("command can't be empty! type something!");
            }
            throw new DackelException("\"" + commandArgs.get(COMMAND) + "\" is not a recognized command.");
        }
        return true;
    }

    /**
     * Reads the saved tasks from the file location DATA_PATH and puts them into storedTasks.
     */
    public static void readTasksFromFile() throws IOException {
        reader = new BufferedReader(new FileReader(DATA_PATH));
        String line;
        while ((line = reader.readLine()) != null) {
            parseInput(line);
            String command = commandArgs.get(COMMAND);
            switch (command) {
            case TODO:
                addTodo(commandArgs.get(BODY), commandArgs.containsKey(MARK), true);
                break;
            case DEADLINE:
                addDeadline(commandArgs.get(BODY), commandArgs.get(BY), commandArgs.containsKey(MARK), true);
                break;
            case EVENT:
                addEvent(commandArgs.get(BODY), commandArgs.get(FROM), commandArgs.get(UNTIL), commandArgs.containsKey(MARK), true);
                break;
            default:
                break;
            }
        }
        reader.close();
    }

    /**
     * Write tasks in storedTasks into the file at location DATA_PATH
     */
    public static void writeTasksToFile() throws IOException {
        writer = new PrintWriter(new FileWriter(DATA_PATH));
        for (Task task: storedTasks) {
            writer.println(task.getCommand());
        }
        writer.close();
    }

    public static void main(String[] args) {
        // title cards, etc.
        System.out.println(LINE);
        System.out.println(LOGO);
        System.out.println(VERSION);
        System.out.println(LINE);
        System.out.println();

        // load previously saved tasks
        System.out.println("Loading previously saved tasks...");
        try {
            readTasksFromFile();
            System.out.println("Tasks successfully loaded.");
        }
        catch (FileNotFoundException e) {
            System.out.println(DATA_PATH + " not found. Creating new save file...");
            try {
                writer = new PrintWriter(new FileWriter(DATA_PATH));
                writer.print("");
                writer.close();
                System.out.println(DATA_PATH + " created.");
            }
            catch (IOException ee) {
                System.out.println("The file " + DATA_PATH + " could not be created. Check if there are other files in the directory with similar names.");
            }
        }
        catch (IOException e) {
            System.out.println("Unknown error occurred while reading the file.");
            System.out.println("Continuing execution without saved data.");
        }

        // greeting message
        speak("hihi!");
        speak("what can i do for you today?");
        
        // main command entry loop
        // TODO: make dackel read its lines from a file
        boolean isNotQuit = true;
        while (isNotQuit) {
            try {
                String input = receiveInput();
                isNotQuit = executeInput(input);
            }
            catch (DackelException e) {
                speak(e.getMessage());
            }
        }

        // goodbye message
        speak("hope to see you again soon!");
    }
}
