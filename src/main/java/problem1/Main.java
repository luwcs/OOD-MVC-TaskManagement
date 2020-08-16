package problem1;

/**
 * problem1.Main class controls flow of process to-do data.
 */
public class Main {

  /**
   * The main function controls flow of process to-do data
   *
   * @param args input info from command line
   */
  public static void main(String[] args) {
    TodoArgData tad = TodoArgParser.parse(args);

    if (!tad.isValid()) {
      // print out detailed error info
      tad.displayDetailError();
      TodoArgParser.displayInstruction();
    }

    // Do the functionality in a particular order
    // First add, then complete, last display

    TodoManager tm = new TodoManager(tad.getTodoFilePath());

    if (tad.needToAdd()) {
      tm.addTodo(tad.getTodoToAdd());
    }

    if (tad.needToComplete()) {
      tm.completeTodo(tad.getListOfCompleted());
    }

    if (tad.needToDisplay()) {
      tm.displayTodoByRequirement(tad.getSortingType(), tad.onlyShowIncomplete(),
          tad.getShowByCategory());
    }

    // only write the file once
    if (tad.needToAdd() || tad.needToComplete()) {
      tm.updateTodosToCSV();
    }
  }
}
