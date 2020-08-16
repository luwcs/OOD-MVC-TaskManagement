package problem1;

import java.time.LocalDate;
import java.util.Objects;

/**
 * To-do class presents the specific to-do task
 */
public class Todo {

  private final int id;
  private final String text;
  private boolean completed;
  private final LocalDate dueDate;
  private final PriorityEnum priority;
  private final String category;

  /**
   * The constructor of the To-do
   *
   * @param id        the id of To-do task
   * @param text      the a description of the task to be done
   * @param completed indicates whether the task is completed or incomplete.
   * @param dueDate   a due date
   * @param priority  an integer indicating the priority of the task.
   * @param category  a user-specified String that can be used to group related task.
   */
  public Todo(int id, String text, boolean completed, LocalDate dueDate, PriorityEnum priority,
      String category) {
    this.id = id;
    this.text = text;
    this.completed = completed;
    this.dueDate = dueDate;
    this.priority = priority;
    this.category = category;
  }

  /**
   * Constructor of the To-do task
   *
   * @param id   the id of To-do task
   * @param todo a new task
   */
  public Todo(int id, Todo todo) {
    this.id = id;
    this.text = todo.getText();
    this.completed = todo.getCompletedStatus();
    this.dueDate = todo.getDueDate();
    this.priority = todo.getPriority();
    this.category = todo.getCategory();
  }

  /**
   * Gets the task id
   *
   * @return the id of the task
   */
  public int getId() {
    return this.id;
  }

  /**
   * Gets the text of task
   *
   * @return the text of task
   */
  public String getText() {
    return this.text;
  }

  /**
   * Checks whether the task is completed
   *
   * @return true of the task is completed, otherwise return false.
   */
  public boolean getCompletedStatus() {
    return this.completed;
  }

  /**
   * Sets the completed status
   *
   * @param completed true for completed
   */
  public void setCompletedStatus(boolean completed) {
    this.completed = completed;
  }

  /**
   * Gets the due date of the task
   *
   * @return the due date
   */
  public LocalDate getDueDate() {
    return this.dueDate;
  }

  /**
   * Gets the priority of the task
   *
   * @return an problem1.PriorityEnum suggesting the priority of the task
   */
  public PriorityEnum getPriority() {
    return this.priority;
  }

  public String getCategory() {
    return this.category;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return "problem1.Todo{" +
        "id=" + id +
        ", text=" + text +
        ", completed=" + completed +
        ", dueDate=" + dueDate +
        ", priority=" + priority +
        ", category='" + category + '\'' +
        '}';
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Todo todo = (Todo) o;

    return id == todo.id &&
        text.equals(todo.text) &&
        completed == todo.completed &&
        Objects.equals(dueDate, todo.dueDate) &&
        priority == todo.priority &&
        Objects.equals(category, todo.category);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    return Objects.hash(id, text, completed, dueDate, priority, category);
  }
}
