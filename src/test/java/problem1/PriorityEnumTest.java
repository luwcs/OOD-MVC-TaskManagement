package problem1;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import problem1.PriorityEnum;

public class PriorityEnumTest {

  @Test
  public void getPriorityEnumFromNumber() {
    assertEquals(PriorityEnum.ONE, PriorityEnum.getPriorityEnumFromNumber(1));
    assertEquals(PriorityEnum.TWO, PriorityEnum.getPriorityEnumFromNumber(2));
    assertEquals(PriorityEnum.THREE, PriorityEnum.getPriorityEnumFromNumber(3));
    assertEquals(PriorityEnum.NON_EXIST, PriorityEnum.getPriorityEnumFromNumber(4));
  }
}