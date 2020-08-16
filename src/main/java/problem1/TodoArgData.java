package problem1;

import java.util.List;
import java.util.Objects;

/**
 * A ToDoArgData object that stores the information parsed from the command line
 */
public class TodoArgData {

  private String invalidArg;
  private String csvPath;
  private Todo toBeAdd;
  private List<Integer> completedIDList;
  private boolean needDisplay;
  private boolean onlyShowIncomplete;
  private String showByCategory;
  private SortingEnum sortBy;

  /**
   * Creates a ToDoArgData with specified information parsed from the command line
   *
   * @param csvPath            the file path to store ToDos
   * @param addTodoList        a list store a new To-Do to add
   * @param completedIDList    a list of completed To-Dos
   * @param needDisplay        whether we need to display the to-dos
   * @param onlyShowIncomplete whether we only need to show incomplete to-dos
   * @param showByCategory     the category as a filter to display to-dos
   * @param sortBy             the type of property to sort by when displaying our to-dos
   */
  public TodoArgData(String csvPath, List<Todo> addTodoList, List<Integer> completedIDList,
      boolean needDisplay, boolean onlyShowIncomplete, String showByCategory, SortingEnum sortBy) {
    this.invalidArg = null;
    this.csvPath = csvPath;
    this.toBeAdd = addTodoList.isEmpty() ? null : addTodoList.get(0);
    this.completedIDList = completedIDList;
    this.needDisplay = needDisplay;
    this.onlyShowIncomplete = onlyShowIncomplete;
    this.showByCategory = showByCategory;
    this.sortBy = sortBy;
  }

  /**
   * A special constructor used to initial invalid data
   * @param invalidArg the arguments that not valid
   */
  protected TodoArgData(String invalidArg) {
    this.invalidArg = invalidArg;
  }

  /**
   * Return whether the data is a valid input
   * @return whether the data is a valid input
   */
  protected boolean isValid() {
    return invalidArg == null && csvPath != null && (!needToAdd() ||
        (toBeAdd.getText() != null && toBeAdd.getPriority() != PriorityEnum.NON_EXIST));
  }

  /**
   * Return whether we need to add a new To-do
   * @return whether we need to add a new To-do
   */
  protected boolean needToAdd() {
    return toBeAdd != null;
  }

  /**
   * Return whether we need to complete some to-dos
   * @return whether we need to complete some to-dos
   */
  protected boolean needToComplete() {
    return !completedIDList.isEmpty();
  }

  /**
   * Return whether we need to display to-dos
   * @return whether we need to display to-dos
   */
  protected boolean needToDisplay() {
    return needDisplay;
  }

  /**
   * Return whether we only display incomplete items
   * @return whether we only display incomplete items
   */
  protected boolean onlyShowIncomplete() {
    return onlyShowIncomplete;
  }

  /**
   * Display detail error information
   */
  protected void displayDetailError() {
    if (this.invalidArg != null) {
      System.out.println("Error:\nCommand not found: " + this.invalidArg);
      return;
    }
    if (this.csvPath == null) {
      System.out.println("Error:\nPlease indicate the path of a CSV file.");
      return;
    }
    if (this.needToAdd()) {
      if (this.toBeAdd.getText() == null) {
        System.out.println("Error:\nPlease enter a description for the new todo.");
        return;
      }
      if (this.toBeAdd.getPriority() == PriorityEnum.NON_EXIST) {
        System.out.println("Error:\nYou can only set priority from 1 to 3.");
      }
    }
  }

  /**
   * Return the path of csv file
   * @return the path of csv file
   */
  public String getTodoFilePath() {
    return csvPath;
  }

  /**
   * Return the new to-do to add
   * @return the new to-do to add
   */
  public Todo getTodoToAdd() {
    return toBeAdd;
  }

  /**
   * Return the list of completed items
   * @return the list of completed items
   */
  public List<Integer> getListOfCompleted() {
    return completedIDList;
  }

  /**
   * Return the category to show when display
   * @return the category to show when display
   */
  public String getShowByCategory() {
    return showByCategory;
  }

  /**
   * Return the type to sort by when display
   * @return the type to sort by when display
   */
  public SortingEnum getSortingType() {
    return sortBy;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof TodoArgData)) {
      return false;
    }
    TodoArgData that = (TodoArgData) o;
    return needDisplay == that.needDisplay &&
        onlyShowIncomplete == that.onlyShowIncomplete &&
        Objects.equals(invalidArg, that.invalidArg) &&
        Objects.equals(csvPath, that.csvPath) &&
        Objects.equals(toBeAdd, that.toBeAdd) &&
        Objects.equals(completedIDList, that.completedIDList) &&
        Objects.equals(showByCategory, that.showByCategory) &&
        sortBy == that.sortBy;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    return Objects.hash(invalidArg, csvPath, toBeAdd, completedIDList,
            needDisplay, onlyShowIncomplete, showByCategory, sortBy);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return "problem1.TodoArgData{" +
        "invalidArg='" + invalidArg + '\'' +
        ", csvPath='" + csvPath + '\'' +
        ", toBeAdd=" + toBeAdd +
        ", completedIDList=" + completedIDList +
        ", needDisplay=" + needDisplay +
        ", onlyShowIncomplete=" + onlyShowIncomplete +
        ", showByCategory='" + showByCategory + '\'' +
        ", sortBy=" + sortBy +
        '}';
  }
}
