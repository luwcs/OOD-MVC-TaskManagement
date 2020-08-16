package problem1;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import org.junit.Before;
import org.junit.Test;
import problem1.PriorityEnum;
import problem1.SortingEnum;
import problem1.Todo;
import problem1.TodoArgData;

public class TodoArgDataTest {
  Todo todo1;
  TodoArgData invalidData;
  Todo todo2;
//  problem1.Todo todo3;
//  problem1.Todo todo4;
//  problem1.Todo todo5;
//  problem1.Todo todo6;


//    todo3 = new problem1.Todo(2, "Mail passport", true,
//        LocalDate.of(2020, 2, 28), null, "?");
//    todo4 = new problem1.Todo(3, "Study for finals", false,
//        null, problem1.PriorityEnum.TWO, "school"));
//    todo5 = new problem1.Todo(4, "Clean the house", false,
//        LocalDate.of(2020, 3, 22), problem1.PriorityEnum.THREE, "home");
//    todo6 = new problem1.Todo(5, "Buy yarn for blanket, stuffed toy", true,
//        null, null, "home");

  TodoArgData argData;

  @Before
  public void setUp() throws Exception {
    todo1 = new Todo(0, "Wash the dishes", false,
        LocalDate.of(2020, 3, 22), PriorityEnum.TWO, "house");
    todo2 = new Todo(1, "Finish HW9", false,
        LocalDate.of(2020, 3, 22), PriorityEnum.ONE, "school");
    invalidData = new TodoArgData("NoWalala");
    argData = new TodoArgData("tasks.txt", Arrays.asList(todo1), Arrays.asList(2, 4),
        true, false, "house", SortingEnum.Priority);
  }
//    "1","Finish HW9","false","3/22/2020","1","school"
//    "2","Mail passport","true","2/28/2020","?","?"
//    "3","Study for finals","false","?","2","school"
//    "4","Clean the house","false","3/22/2020","3","home"
//    "5","Buy yarn for blanket, stuffed toy","true","?","?","home"

  /**
   * new problem1.TodoArgData(csvPath, addTodoList, completeTodoIDList,
   *         needDisplay, onlyShowIncomplete, showByCategory, sortBy)
   */

  @Test
  public void isValid() {
    assertFalse(invalidData.isValid());
    TodoArgData invalidData1 = new TodoArgData(null, Arrays.asList(todo1), Arrays.asList(2, 4),
        true, false, "house", SortingEnum.Priority);
    assertFalse(invalidData1.isValid());
    assertFalse(new TodoArgData("tasks.txt", Arrays.asList(new Todo(1, null, false,
        LocalDate.of(2020, 3, 22), PriorityEnum.ONE, "school")), Arrays.asList(2, 4),
        true, false, "house", SortingEnum.Priority).isValid());
    assertFalse(new TodoArgData("tasks.txt", Arrays.asList(new Todo(1, "finish hw9", false,
        LocalDate.of(2020, 3, 22), PriorityEnum.NON_EXIST, "school")), Arrays.asList(2, 4),
        true, false, "house", SortingEnum.Priority).isValid());
    assertTrue(argData.isValid());
  }
//  argData = new problem1.TodoArgData("tasks.txt", Arrays.asList(todo1), Arrays.asList(2, 4),
//        true, false, "house", problem1.SortingEnum.Priority);

  @Test
  public void needToAdd() {
    assertTrue(argData.needToAdd());
    assertFalse(invalidData.needToAdd());
  }

  @Test
  public void needToComplete() {
    assertTrue(argData.needToComplete());
    assertFalse(new TodoArgData("tasks.txt", Arrays.asList(todo1), new ArrayList<>(),
        true, false, "house", SortingEnum.Priority).needToComplete());
  }

  @Test
  public void needToDisplay() {
    assertTrue(argData.needToDisplay());
  }

  @Test
  public void onlyShowIncomplete() {
    assertFalse(argData.onlyShowIncomplete());
  }

  @Test
  public void getTodoFilePath() {
    assertEquals("tasks.txt", argData.getTodoFilePath());
  }

  @Test
  public void getTodoToAdd() {
    assertEquals(new Todo(0, "Wash the dishes", false,
        LocalDate.of(2020, 3, 22), PriorityEnum.TWO, "house"),
        argData.getTodoToAdd());
  }

  @Test
  public void getListOfCompleted() {
    assertEquals(Arrays.asList(2, 4), argData.getListOfCompleted());
  }

  @Test
  public void getShowByCategory() {
    assertEquals("house", argData.getShowByCategory());
  }

  @Test
  public void getSortingType() {
    assertEquals(SortingEnum.Priority, argData.getSortingType());
  }

  @Test
  public void testEquals() {
    assertFalse(argData.equals(null));
    assertFalse(argData.equals(new LinkedList<>()));
    assertTrue(argData.equals(argData));
    assertFalse(argData.equals(new TodoArgData("false.txt", Arrays.asList(todo1), Arrays.asList(2, 4),
        true, false, "house", SortingEnum.Priority)));
    assertFalse(argData.equals(new TodoArgData("tasks.txt", Arrays.asList(todo2), Arrays.asList(2, 4),
        true, false, "house", SortingEnum.Priority)));
    assertFalse(argData.equals(new TodoArgData("tasks.txt", Arrays.asList(todo1), Arrays.asList(1, 4),
        true, false, "house", SortingEnum.Priority)));
    assertFalse(argData.equals(new TodoArgData("tasks.txt", Arrays.asList(todo1), Arrays.asList(2, 4),
        false, false, "house", SortingEnum.Priority)));
    assertFalse(argData.equals(new TodoArgData("tasks.txt", Arrays.asList(todo1), Arrays.asList(2, 4),
        true, true, "house", SortingEnum.Priority)));
    assertFalse(argData.equals(new TodoArgData("tasks.txt", Arrays.asList(todo1), Arrays.asList(2, 4),
        true, false, "walaal", SortingEnum.Priority)));
    assertFalse(argData.equals(new TodoArgData("tasks.txt", Arrays.asList(todo1), Arrays.asList(2, 4),
        true, false, "house", SortingEnum.Date)));
    assertFalse(argData.equals(invalidData));
    assertTrue(argData.equals(new TodoArgData("tasks.txt", Arrays.asList(todo1), Arrays.asList(2, 4),
        true, false, "house", SortingEnum.Priority)));
  }

  @Test
  public void testHashCode() {
    assertEquals(new TodoArgData("tasks.txt", Arrays.asList(todo1), Arrays.asList(2, 4),
        true, false, "house", SortingEnum.Priority).hashCode(),
        argData.hashCode());
  }

  @Test
  public void testToString() {
    String expected = "problem1.TodoArgData{invalidArg='null', csvPath='tasks.txt', toBeAdd=problem1.Todo{id=0, "
        + "text=Wash the dishes, completed=false, dueDate=2020-03-22, priority=TWO, category='house'},"
        + " completedIDList=[2, 4], needDisplay=true, onlyShowIncomplete=false, showByCategory='house', "
        + "sortBy=Priority}";
    assertEquals(expected, argData.toString());
  }
}