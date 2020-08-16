package problem1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * problem1.TodoManager controls some functionality of control to-do task
 */
public class TodoManager {

  private String csvPath;
  private List<Todo> allTodos;

  /**
   * the constructor of the problem1.TodoManager
   *
   * @param csvPath the path of the csv
   */
  public TodoManager(String csvPath) {
    this.csvPath = csvPath;
    this.allTodos = getTodoList();
  }

  /**
   * Add new to-do into list
   *
   * @param toBeAdd to-do to be added
   */
  public void addTodo(Todo toBeAdd) {
    Todo todoWithId = new Todo(allTodos.size() + 1, toBeAdd);
    allTodos.add(todoWithId);
  }

  /**
   * Check the completed to-dos.
   *
   * @param completedIDs IDs of completed todos.
   */
  public void completeTodo(List<Integer> completedIDs) {
    for (Todo todo : this.allTodos) {
      if (completedIDs.contains(todo.getId())) {
        todo.setCompletedStatus(true);
      }
    }
  }

  /**
   * Update todos to csv file.
   */
  public void updateTodosToCSV() {
    allTodos.sort(new IdComparator());
    try (BufferedWriter updateOutput = new BufferedWriter(new FileWriter(this.csvPath))) {
      // count the number of remaining lines that need to be written
      int count = allTodos.size() + 1;
      int index = 0;
      String header = "\"id\",\"text\",\"completed\",\"due\",\"priority\",\"category\"";
      updateOutput.write(header + System.lineSeparator());
      while (count > 0) {
        updateOutput.write(writeLineFromTodo(allTodos.get(index++)));
        count--;
      }
    } catch (FileNotFoundException fnfe) {
      System.out.println("Cannot find the file: " + fnfe.getMessage());
      fnfe.printStackTrace();
    } catch (IOException ioe) {
      System.out.println("Something went wrong in IO: " + ioe.getMessage());
      ioe.printStackTrace();
    }
  }

  /**
   * Helper function to convert to-do to the format of csv lines.
   *
   * @param todo to-do
   * @return line to be written in csv.
   */
  protected String writeLineFromTodo(Todo todo) {
    String id = String.valueOf(todo.getId());
    String text = todo.getText();
    String completed = String.valueOf(todo.getCompletedStatus());
    LocalDate dueDate = todo.getDueDate();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/uuuu");
    String date = dueDate.format(formatter);

    String priority = String.valueOf(todo.getPriority().priority);
    String category = todo.getCategory();

    String sep = "\",\"";
    return "\"" + id + sep + text + sep + completed + sep + date + sep + priority + sep
        + category + "\"";
  }

  /**
   * getTodoList gets the to-do list
   *
   * @return a update list of to-do tasks
   */
  public List<Todo> getTodoList() {
    if (this.allTodos == null) {
      this.allTodos = new ArrayList<>();
      try (BufferedReader csvFile = new BufferedReader(new FileReader(this.csvPath))) {
        // Read the first line of CSV file
        csvFile.readLine();
        String line;
        while ((line = csvFile.readLine()) != null) {
          this.allTodos.add(createTodoFromLine(line));
        }
      } catch (FileNotFoundException fnfe) {
        System.out.println("The file was not found : " + fnfe.getMessage());
        fnfe.printStackTrace();
      } catch (IOException ioe) {
        System.out.println("Something went wrong! : " + ioe.getMessage());
        ioe.printStackTrace();
      }
    }
    return this.allTodos;
  }

  /**
   * createTodoFromLine is the helper methods that create the to-do object based on the csv line
   *
   * @param line information about the task
   * @return the To-do task object
   */
  private Todo createTodoFromLine(String line) {
    String[] strArr = line.substring(1, line.length() - 1).split("\",\"");
    int id = Integer.parseInt(strArr[0]);
    String text = strArr[1];
    boolean isCompleted = "true".equals(strArr[2]);

    // The date is represented as DD/MM/YYYY, LocalDate of(int year, int month, int dayOfMonth)
    LocalDate dueDate = null;
    try {
      String[] date = strArr[3].split("/");
      dueDate = LocalDate
          .of(Integer.parseInt(date[2]), Integer.parseInt(date[0]), Integer.parseInt(date[1]));
    } catch (Exception e) {
      System.out.println("[Warning]Cannot get due date: " + e.getMessage());
    }

    //get the problem1.PriorityEnum
    PriorityEnum priority = null;
    try {
      priority = PriorityEnum.getPriorityEnumFromNumber(Integer.parseInt(strArr[4]));
    } catch (Exception e) {
      System.out.println("[Warning]Cannot get for priority: " + e.getMessage());
    }
    String category = strArr[5];
    return new Todo(id, text, isCompleted, dueDate, priority, category);
  }

  /**
   * displayTodoByRequirement prints out the To-do task based on the specific requirement
   *
   * @param sortType       Sort by what type
   * @param onlyIncomplete should we only display incomplete todos
   * @param category       what category to be displayed
   */
  public void displayTodoByRequirement(SortingEnum sortType, boolean onlyIncomplete,
      String category) {
    if (!onlyIncomplete && category == null) {
      displayTodoBySorting(sortType, this.allTodos);
      return;
    }

    List<Todo> toBeDisplay = new ArrayList<>();

    for (Todo todo : this.allTodos) {
      boolean shouldAdd;
      if (onlyIncomplete && category != null) {
        shouldAdd = !todo.getCompletedStatus() && category.toLowerCase()
            .equals(todo.getCategory().toLowerCase());
      } else if (onlyIncomplete) {
        shouldAdd = !todo.getCompletedStatus();
      } else {
        shouldAdd = category.toLowerCase().equals(todo.getCategory().toLowerCase());
      }
      if (shouldAdd) {
        toBeDisplay.add(todo);
      }
    }
    displayTodoBySorting(sortType, toBeDisplay);
  }

  /**
   * displayTodoBySorting prints out the To-do task based on the specific sorting requirement
   *
   * @param sortType    Sort by which type
   * @param toBeDisplay The list to be displayed
   */
  private void displayTodoBySorting(SortingEnum sortType, List<Todo> toBeDisplay) {
    SortingEnum nonNullType = sortType == null ? SortingEnum.None : sortType;
    switch (nonNullType) {
      // Sort the list by date
      case Date:
        Collections.sort(toBeDisplay, new DateComparator());
        break;
      // Sort the list by priority
      case Priority:
        Collections.sort(toBeDisplay, new PriorityComparator());
        break;
      case None:
      default:
        break;
    }
    // display the to do list
    for (Todo todo : toBeDisplay) {
      System.out.println(todo);
    }
    // At the end, sort the list with IdComparator so we don't mess things up.
    Collections.sort(toBeDisplay, new IdComparator());
  }

  /**
   * Private class DateComparator override the compare method based on date of the task. The early
   * date is at the top. If the date is null, then list it at the bottom.
   */
  private static class DateComparator implements Comparator<Todo> {

    @Override
    public int compare(Todo td1, Todo td2) {
      LocalDate date1 = td1.getDueDate();
      LocalDate date2 = td2.getDueDate();
      if (date1 == null && date2 == null) {
        return td1.getId() - td2.getId();
      }
      if (date1 == null) {
        return 1;
      }
      if (date2 == null) {
        return -1;
      }
      return date1.compareTo(date2);
    }
  }

  /**
   * Private class IdComparator override the compare method based on id of the task.
   */
  private static class IdComparator implements Comparator<Todo> {

    @Override
    public int compare(Todo td1, Todo td2) {
      return td1.getId() - td2.getId();
    }
  }

  /**
   * Private class PriorityComparator  override the compare method based on priority of the task.
   * i.e. The lowest priority is at the top while the highest priority is at the bottom. If the
   * priority is null, then it is at the top.
   */
  private static class PriorityComparator implements Comparator<Todo> {

    @Override
    public int compare(Todo td1, Todo td2) {
      PriorityEnum p1 = td1.getPriority();
      PriorityEnum p2 = td2.getPriority();

      if (p1 == null && p2 == null) {
        return td1.getId() - td2.getId();
      }
      if (p1 == null) {
        return -1;
      }
      if (p2 == null) {
        return 1;
      }
      return td2.getPriority().ordinal() - td1.getPriority().ordinal();
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || !(o instanceof TodoManager)) {
      return false;
    }
    TodoManager that = (TodoManager) o;
    return Objects.equals(csvPath, that.csvPath) &&
        Objects.equals(allTodos, that.allTodos);
  }

  @Override
  public int hashCode() {
    return Objects.hash(csvPath, allTodos);
  }

  @Override
  public String toString() {
    return "problem1.TodoManager{" +
        "csvPath='" + csvPath + '\'' +
        ", allTodos=" + allTodos +
        '}';
  }
}


