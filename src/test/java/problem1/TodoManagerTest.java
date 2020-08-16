package problem1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import problem1.PriorityEnum;
import problem1.SortingEnum;
import problem1.Todo;
import problem1.TodoManager;

public class TodoManagerTest {

  TodoManager todoManger;
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
  private final PrintStream originalErr = System.err;

  @Before
  public void setUp() throws Exception {
    todoManger = new TodoManager("todos.csv");
    // Capture the system output
    // https://stackoverflow.com/questions/1119385/junit-test-for-system-out-println
    System.setOut(new PrintStream(outContent));
    System.setErr(new PrintStream(errContent));
  }

  @After
  public void restoreStreams() {
    System.setOut(originalOut);
    System.setErr(originalErr);
  }

  @Test
  public void addTodo() {
    //    "6","Read book","false","3/22/2020","2","school"
    todoManger.addTodo(new Todo(0, "Read book", false,
        LocalDate.of(2020, 3, 22), PriorityEnum.TWO, "school"));
    List<Todo> actual = todoManger.getTodoList();

    List<Todo> expected = todoManger.getTodoList();
    expected.add(new Todo(6, "Read book", false,
        LocalDate.of(2020, 3, 22), PriorityEnum.TWO, "school"));

    assertEquals(expected,actual);
  }

  @Test
  public void completeTodo() {
    todoManger.completeTodo(Arrays.asList(1));
    Todo actual = todoManger.getTodoList().get(0);

    Todo expected = new Todo(1, "Finish HW9", true,
        LocalDate.of(2020, 3, 22), PriorityEnum.ONE, "school");

    assertEquals(expected,actual);
  }

  @Test
  public void writeLineFromTodo() {
    Todo todo = new Todo(1, "Finish HW9", false,
        LocalDate.of(2020, 3, 22), PriorityEnum.ONE, "school");

    String expected = "\"1\",\"Finish HW9\",\"false\",\"3/22/2020\",\"1\",\"school\"";
    assertEquals(expected, todoManger.writeLineFromTodo(todo));
  }

  @Test
  public void getTodoList() {
//    "1","Finish HW9","false","3/22/2020","1","school"
//    "2","Mail passport","true","2/28/2020","?","?"
//    "3","Study for finals","false","?","2","school"
//    "4","Clean the house","false","3/22/2020","3","home"
//    "5","Buy yarn for blanket, stuffed toy","true","?","?","home"
    List<Todo> expected = new ArrayList<>();

    expected.add(new Todo(1, "Finish HW9", false,
        LocalDate.of(2020, 3, 22), PriorityEnum.ONE, "school"));
    expected.add(new Todo(2, "Mail passport", true,
        LocalDate.of(2020, 2, 28), null, "?"));
    expected.add(new Todo(3, "Study for finals", false,
        null, PriorityEnum.TWO, "school"));
    expected.add(new Todo(4, "Clean the house", false,
        LocalDate.of(2020, 3, 22), PriorityEnum.THREE, "home"));
    expected.add(new Todo(5, "Buy yarn for blanket, stuffed toy", true,
        null, null, "home"));

    List<Todo> actual = todoManger.getTodoList();
    for (int i = 0; i < 5; i++) {
      assertEquals(expected.get(i), actual.get(i));
    }
  }

  @Test
  public void getTodoListFileNotFound() {
    TodoManager tm = new TodoManager("NoSuchFile");
    assertEquals(0, tm.getTodoList().size());
  }

  @Test
  public void displayTodoByRequirementByDateOnly() {
    todoManger.displayTodoByRequirement(SortingEnum.Date, false, null);
    String expected =
        "problem1.Todo{id=2, text=Mail passport, completed=true, dueDate=2020-02-28, priority=null, category='?'}\n"
            + "problem1.Todo{id=1, text=Finish HW9, completed=false, dueDate=2020-03-22, priority=ONE, category='school'}\n"
            + "problem1.Todo{id=4, text=Clean the house, completed=false, dueDate=2020-03-22, priority=THREE, category='home'}\n"
            + "problem1.Todo{id=3, text=Study for finals, completed=false, dueDate=null, priority=TWO, category='school'}\n"
            + "problem1.Todo{id=5, text=Buy yarn for blanket, stuffed toy, completed=true, dueDate=null, priority=null, category='home'}\n";
    assertEquals(expected, outContent.toString());
  }

  @Test
  public void displayTodoByRequirementByPriorityOnly() {
    todoManger.displayTodoByRequirement(SortingEnum.Priority, false, null);
    String expected =
        "problem1.Todo{id=2, text=Mail passport, completed=true, dueDate=2020-02-28, priority=null, category='?'}\n"
            + "problem1.Todo{id=5, text=Buy yarn for blanket, stuffed toy, completed=true, dueDate=null, priority=null, category='home'}\n"
            + "problem1.Todo{id=4, text=Clean the house, completed=false, dueDate=2020-03-22, priority=THREE, category='home'}\n"
            + "problem1.Todo{id=3, text=Study for finals, completed=false, dueDate=null, priority=TWO, category='school'}\n"
            + "problem1.Todo{id=1, text=Finish HW9, completed=false, dueDate=2020-03-22, priority=ONE, category='school'}\n";
    assertEquals(expected, outContent.toString());
  }

  @Test
  public void displayTodoBySortingPriorityIncompleteNoCategory() {
    todoManger.displayTodoByRequirement(SortingEnum.Priority, true, null);
    String expected =
        "problem1.Todo{id=4, text=Clean the house, completed=false, dueDate=2020-03-22, priority=THREE, category='home'}\n"
            + "problem1.Todo{id=3, text=Study for finals, completed=false, dueDate=null, priority=TWO, category='school'}\n"
            + "problem1.Todo{id=1, text=Finish HW9, completed=false, dueDate=2020-03-22, priority=ONE, category='school'}\n";
    assertEquals(expected, outContent.toString());
  }

  @Test
  public void displayTodoBySortingNoneIncompleteHome() {
    todoManger.displayTodoByRequirement(SortingEnum.None, true, "home");
    String expected =
        "problem1.Todo{id=4, text=Clean the house, completed=false, dueDate=2020-03-22, priority=THREE, category='home'}\n";
    assertEquals(expected, outContent.toString());
  }

  @Test
  public void displayTodoBySortingNoneHome() {
    todoManger.displayTodoByRequirement(null, false, "home");
    String expected =
        "problem1.Todo{id=4, text=Clean the house, completed=false, dueDate=2020-03-22, priority=THREE, category='home'}\n"
            + "problem1.Todo{id=5, text=Buy yarn for blanket, stuffed toy, completed=true, dueDate=null, priority=null, category='home'}\n";
    assertEquals(expected, outContent.toString());
  }

  @Test
  public void testHashCode() {
    TodoManager same = new TodoManager("todos.csv");
    assertEquals(todoManger.hashCode(), same.hashCode());
  }

  @Test
  public void testToString() {
    String expected = "problem1.TodoManager{csvPath='todos.csv', allTodos=["
        + "problem1.Todo{id=1, text=Finish HW9, completed=false, dueDate=2020-03-22, priority=ONE, category='school'}, "
        + "problem1.Todo{id=2, text=Mail passport, completed=true, dueDate=2020-02-28, priority=null, category='?'}, "
        + "problem1.Todo{id=3, text=Study for finals, completed=false, dueDate=null, priority=TWO, category='school'}, "
        + "problem1.Todo{id=4, text=Clean the house, completed=false, dueDate=2020-03-22, priority=THREE, category='home'}, "
        + "problem1.Todo{id=5, text=Buy yarn for blanket, stuffed toy, completed=true, dueDate=null, priority=null, category='home'}]}";
    assertEquals(expected, todoManger.toString());
  }

  @Test
  public void testEquals() {
    assertTrue(todoManger.equals(todoManger));
    assertFalse(todoManger.equals(null));
    assertFalse(todoManger.equals("Str"));
    TodoManager same = new TodoManager("todos.csv");
    assertTrue(todoManger.equals(same));
    TodoManager diff = new TodoManager("diff");
    assertFalse(todoManger.equals(diff));
    diff = new TodoManager("todos.csv");
    diff.addTodo(new Todo(1, "Finish hw9", true, LocalDate.of(2020, 1, 22), PriorityEnum.TWO,
        "school"));
    assertFalse(todoManger.equals(diff));
  }
}