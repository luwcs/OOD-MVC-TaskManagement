package problem1;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import org.junit.Test;
import problem1.PriorityEnum;
import problem1.SortingEnum;
import problem1.Todo;
import problem1.TodoArgData;
import problem1.TodoArgParser;

public class TodoArgParserTest {
  private String[] VALID_ARGS_ONE = ("--csv-file tasks.csv --add-todo --todo-text WashDishes --completed --due 2020-03-22 --priority 2 --category house --complete-todo 3 --complete-todo 4 --display --show-incomplete --show-category house --sort-by-date").split(" ");
  private String[] VALID_ARGS_TWO = ("--csv-file tasks.csv --add-todo --todo-text WashDishes --completed --due 2020-03-22 --priority 2 --category house --complete-todo 3 --complete-todo 4 --display --show-incomplete --show-category house --sort-by-priority").split(" ");
  private String[] INVALID_ARGS_DUE = ("--csv-file tasks.csv --add-todo --todo-text WashDishes --completed --due invalid --priority 2 --category house --complete-todo 3 --complete-todo 4 --display --show-incomplete --show-category house --sort-by-date").split(" ");
  private String[] INVALID_ARGS_PRIORITY = ("--csv-file tasks.csv --add-todo --todo-text WashDishes --completed --due 2020-03-22 --priority invalid --category house --complete-todo 3 --complete-todo 4 --display --show-incomplete --show-category house --sort-by-date").split(" ");
  private String[] INVALID_ARGS_COMMAND = ("--csv-file tasks.csv --add-todo --todo-text WashDishes --completed --due 2020-03-22 --priority invalid --category house --complete-todo --display --show-incomplete --show-category house --sort-by-date").split(" ");
  private String[] INVALID_ARGS_UNMBER = ("--csv-file tasks.csv --add-todo --todo-text WashDishes --completed --due 2020-03-22 --priority 2 --category house --complete-todo invalid --complete-todo 4 --display --show-incomplete --show-category house --sort-by-date").split(" ");
  private String[] INVALID_ARGS_UNKOWN = ("--csv-file tasks.csv NotACommand").split(" ");

  @Test
  public void parse() {

    Todo todo1 = new Todo(0, "WashDishes", true,
        LocalDate.of(2020, 3, 22), PriorityEnum.TWO, "house");
    TodoArgData expected = new TodoArgData("tasks.csv", Arrays.asList(todo1), Arrays.asList(3, 4),
        true, true, "house", SortingEnum.Date);
    TodoArgData actual = TodoArgParser.parse(VALID_ARGS_ONE);
    assertEquals(expected, actual);

    TodoArgData expectedValidTwo = new TodoArgData("tasks.csv", Collections.singletonList(new Todo(0, "WashDishes", true,
        LocalDate.of(2020, 3, 22), PriorityEnum.TWO, "house")), Arrays.asList(3, 4),
        true, true, "house", SortingEnum.Priority);
    assertEquals(expectedValidTwo, TodoArgParser.parse(VALID_ARGS_TWO));

    TodoArgData expectedInvalidDue = new TodoArgData("tasks.csv", Arrays.asList(new Todo(0, "WashDishes", true,
        null, PriorityEnum.TWO, "house")), Arrays.asList(3, 4),
        true, true, "house", SortingEnum.Date);
    assertEquals(expectedInvalidDue, TodoArgParser.parse(INVALID_ARGS_DUE));

    TodoArgData expectedInvalidPriority = new TodoArgData("tasks.csv", Arrays.asList(new Todo(0, "WashDishes", true,
        LocalDate.of(2020, 3, 22), null, "house")), Arrays.asList(3, 4),
        true, true, "house", SortingEnum.Date);
    assertEquals(expectedInvalidPriority, TodoArgParser.parse(INVALID_ARGS_PRIORITY));
 ;
    TodoArgData expectedInvalidCommand = new TodoArgData("You need to provide id of completed To-Do");
    assertEquals(expectedInvalidCommand, TodoArgParser.parse(INVALID_ARGS_COMMAND));

    TodoArgData expectedInvalidNumber = new TodoArgData("You need to provide a number as the id of completed To-Do");
    assertEquals(expectedInvalidNumber, TodoArgParser.parse(INVALID_ARGS_UNMBER));

    TodoArgData expectedNotACommand = new TodoArgData("NotACommand");
    assertEquals(expectedNotACommand, TodoArgParser.parse(INVALID_ARGS_UNKOWN));

  }

}