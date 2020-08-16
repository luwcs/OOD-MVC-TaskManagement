package problem1;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * problem1.TodoArgParser class processes the input argument and prints out instruction.
 */
public class TodoArgParser {

  // store valid options as constants.
  private static final String CSV_PATH = "--csv-file";

  private static final String ADD_TODO = "--add-todo";
  private static final String TODO_TEXT = "--todo-text";
  private static final String COMPLETED = "--completed";
  private static final String DUE = "--due";
  private static final String PRIORITY = "--priority";
  private static final String CATEGORY = "--category";

  private static final String COMPLETE_TODO = "--complete-todo";
  private static final String DISPLAY = "--display";

  private static final String SHOW_INCOMPLETE = "--show-incomplete";
  private static final String SHOW_CATEGORY = "--show-category";
  private static final String SORT_BY_DATE = "--sort-by-date";
  private static final String SORT_BY_PRIORITY = "--sort-by-priority";

  private static final String[] COMMANDS = {
      CSV_PATH, ADD_TODO, TODO_TEXT, COMPLETED, DUE, PRIORITY, CATEGORY, COMPLETE_TODO,
      DISPLAY, SHOW_INCOMPLETE, SHOW_CATEGORY, SORT_BY_DATE, SORT_BY_PRIORITY};
  // Properties of a new item
  private static final String[] TODO_PROPERTIES = {TODO_TEXT, COMPLETED, DUE, PRIORITY, CATEGORY};

  // parse information of the new item to add, triggered by add command

  /**
   * Function to parse the property of the newly added to-do
   *
   * @param args        the input string array from the command line
   * @param startIndex  the start index to parse the commandline
   * @param addTodoList a list storing the to-do to add
   * @return a index for the main function to continue parsing
   */
  private static int parseTodoInformation(String[] args, int startIndex, List<Todo> addTodoList) {
    String text = null;
    boolean completed = false;
    LocalDate dueDate = null;
    PriorityEnum priority = null;
    String category = null;

    int i = startIndex, len = args.length;
    for (; i < len && isTodoProperty(args[i]); i++) {
      switch (args[i]) {
        case TODO_TEXT:
          if (i + 1 < len && !isCommand(args[i + 1])) {
            text = args[++i];
          }
          break;
        case COMPLETED:
          completed = true;
          break;
        case DUE:
          if (i + 1 < len && !isCommand(args[i + 1])) {
            try {
              dueDate = LocalDate.parse(args[++i]);
            } catch (DateTimeParseException dtpe) {
              System.out.println("Wrong format for the due date");
            }
          }
          break;
        case PRIORITY:
          if (i + 1 < len && !isCommand(args[i + 1])) {
            try {
              int number = Integer.parseInt(args[++i]);
              priority = PriorityEnum.getPriorityEnumFromNumber(number);
            } catch (NumberFormatException nfe) {
              System.out.println("You should enter number format for priority");
            }
          }
          break;
        case CATEGORY:
          if (i + 1 < len && !isCommand(args[i + 1])) {
            category = args[++i];
          }
          break;
      }
    }
    addTodoList.add(new Todo(0, text, completed, dueDate, priority, category));
    return i - 1;
  }

  /**
   * Parse method that processes the input argument.
   *
   * @param args input string array from the command line
   * @return a new problem1.TodoArgData object.
   */
  public static TodoArgData parse(String[] args) {
    // initialize value, which is used to create problem1.TodoArgData object later
    List<Todo> addTodoList = new ArrayList<>();
    List<Integer> completeTodoIDList = new ArrayList<>();

    String csvPath = null;

    boolean needDisplay = false;
    boolean onlyShowIncomplete = false;
    String showByCategory = null;
    SortingEnum sortBy = SortingEnum.None;

    int len = args.length;
    for (int i = 0; i < len; i++) {
      switch (args[i]) {
        case CSV_PATH:
          if (i + 1 < len && !isCommand(args[i + 1])) {
            csvPath = args[++i];
          }
          break;
        case ADD_TODO:
          i = parseTodoInformation(args, i + 1, addTodoList);
          break;
        case COMPLETE_TODO:
          if (i + 1 < len && !isCommand(args[i + 1])) {
            try {
              int number = Integer.parseInt(args[++i]);
              completeTodoIDList.add(number);
            } catch (NumberFormatException nfe) {
              System.out.println("Enter number format for the To-Do ID ");
              return new TodoArgData("You need to provide a number as the id of completed To-Do");
            }
          } else {
            return new TodoArgData("You need to provide id of completed To-Do");
          }
          break;
        case DISPLAY:
          needDisplay = true;
          break;
        case SHOW_INCOMPLETE:
          onlyShowIncomplete = true;
          break;
        case SHOW_CATEGORY:
          if (i + 1 < len && !isCommand(args[i + 1])) {
            showByCategory = args[++i];
          }
          break;
        case SORT_BY_DATE:
          sortBy = SortingEnum.Date;
          break;
        case SORT_BY_PRIORITY:
          sortBy = SortingEnum.Priority;
          break;
        default:
          return new TodoArgData(args[i]);
      }
    }
    return new TodoArgData(csvPath, addTodoList, completeTodoIDList,
        needDisplay, onlyShowIncomplete, showByCategory, sortBy);
  }

  /**
   * Helper function to check whether an argument is a command
   *
   * @param s the argument to check
   * @return if the argument is a command
   */
  private static boolean isCommand(String s) {
    for (String command : COMMANDS) {
      if (command.equals(s)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Helper function to check whether an argument is a property of to-do
   *
   * @param s the argument to check
   * @return if the argument is a property of to-do
   */
  private static boolean isTodoProperty(String s) {
    for (String property : TODO_PROPERTIES) {
      if (property.equals(s)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Display the instruction of usage of this app
   */
  public static void displayInstruction() {
    System.out.println("Usage:\n" +
        "\t--csv-file <path/to/file> The CSV file containing the todos. This option is required.\n"
        +
        "\t--add-todo Add a new todo. If this option is provided, then\n" +
        "\t--todo-text must also be provided.\n" +
        "\t--todo-text <description of todo> A description of the todo.\n" +
        "\t--completed (Optional) Sets the completed status of a new todo to true.\n" +
        "\t--due <due date> (Optional) Sets the due date of a new todo. You may choose how the date should be formatted.\n"
        +
        "\t--priority <1, 2, or 3> (Optional) Sets the priority of a new todo. The value can be 1, 2, or 3.\n"
        +
        "\t--category <a category name> (Optional) Sets the category of a new todo. The value can be any String. Categories do not need to be pre-defined.\n"
        +
        "\t--complete-todo <id> Mark the problem1.Todo with the provided ID as complete.\n" +
        "\t--display Display todos. If none of the following optional arguments are provided, displays all todos.\n"
        +
        "\t--show-incomplete (Optional) If --display is provided, only incomplete todos should be displayed.\n"
        +
        "\t--show-category <category> (Optional) If --display is provided, only todos with the given category should be displayed.\n"
        +
        "\t--sort-by-date (Optional) If --display is provided, sort the list of by date order (ascending). Cannot be combined with --sort-by-priority.\n"
        +
        "\t--sort-by-priority (Optional) If --display is provided, sort the list of todos by priority (ascending). Cannot be combined with --sort-by-date.\n");
  }
}
