package problem1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import problem1.PriorityEnum;
import problem1.Todo;

public class TodoTest {

  Todo todoTest;
  Todo todoTestAnother;

  @Before
  public void setUp() throws Exception {
    LocalDate dueDate = LocalDate.of(2020, 1, 22);
    todoTest = new Todo(1, "Finish hw9", true, dueDate, PriorityEnum.TWO,
        "school");
    todoTestAnother = new Todo(2, todoTest);
  }

  @Test
  public void getId() {
    assertEquals(1, todoTest.getId());
    assertEquals(2, todoTestAnother.getId());
  }

  @Test
  public void getText() {
    assertEquals("Finish hw9", todoTest.getText());
  }

  @Test
  public void getCompletedStatus() {
    assertTrue(todoTest.getCompletedStatus());
  }

  @Test
  public void setCompletedStatus() {
    todoTest.setCompletedStatus(false);
    assertFalse(todoTest.getCompletedStatus());
    todoTest.setCompletedStatus(true);
    assertTrue(todoTest.getCompletedStatus());
  }

  @Test
  public void getDueDate() {
    LocalDate expectedDueDate = LocalDate.of(2020, 1, 22);
    assertEquals(expectedDueDate, todoTest.getDueDate());
  }

  @Test
  public void getPriority() {
    PriorityEnum expectedPriority = PriorityEnum.TWO;
    assertEquals(expectedPriority, todoTest.getPriority());
  }

  @Test
  public void getCategory() {
    assertEquals("school", todoTest.getCategory());
  }

  @Test
  public void testToString() {
    String expected = "problem1.Todo{id=1, text=Finish hw9, completed=true, dueDate=2020-01-22, priority=TWO, category='school'}";
    assertEquals(expected, todoTest.toString());
  }

  @Test
  public void testEquals() {
    assertTrue(todoTest.equals(todoTest));
    assertFalse(todoTest.equals(null));
    assertFalse(todoTest.equals("hello"));

    LocalDate dueDate = LocalDate.of(2020, 1, 22);
    Todo todoTestTwo = new Todo(2, "Finish hw9", true, dueDate, PriorityEnum.TWO,
        "school");

    assertFalse(todoTest.equals(todoTestTwo));

    todoTestTwo = new Todo(1, "Finish hw99", true, dueDate, PriorityEnum.TWO,
        "school");
    assertFalse(todoTest.equals(todoTestTwo));

    todoTestTwo = new Todo(1, "Finish hw9", false, dueDate, PriorityEnum.TWO,
        "school");
    assertFalse(todoTest.equals(todoTestTwo));

    LocalDate dueDateTwo = LocalDate.of(2020, 11, 22);
    todoTestTwo = new Todo(1, "Finish hw9", true, dueDateTwo, PriorityEnum.TWO,
        "school");
    assertFalse(todoTest.equals(todoTestTwo));

    todoTestTwo = new Todo(1, "Finish hw9", true, dueDate, PriorityEnum.ONE,
        "school");
    assertFalse(todoTest.equals(todoTestTwo));

    todoTestTwo = new Todo(1, "Finish hw9", true, dueDate, PriorityEnum.TWO,
        "schooling happy");
    assertFalse(todoTest.equals(todoTestTwo));

  }

  @Test
  public void testHashCode() {
    LocalDate dueDate = LocalDate.of(2020, 1, 22);
    Todo todoTestTwo = new Todo(1, "Finish hw9", true, dueDate, PriorityEnum.TWO,
        "school");
    assertEquals(todoTest.hashCode(), todoTestTwo.hashCode());
  }
}