package problem1;

/**
 * The problem1.PriorityEnum class represents the priority of to-do list
 */
public enum PriorityEnum {

  ONE(1), TWO(2), THREE(3), NON_EXIST(-1);
  int priority;

  /**
   * The constructor of the problem1.PriorityEnum
   *
   * @param priority the priority of the problem1.PriorityEnum, an integer
   */
  PriorityEnum(int priority) {
    this.priority = priority;
  }

  /**
   * getPriorityEnumFromNumber methods return the problem1.PriorityEnum based on the number.
   *
   * @param number input number, an integer.
   * @return the problem1.PriorityEnum based on the specific number.
   */
  public static PriorityEnum getPriorityEnumFromNumber(int number) {
    for (PriorityEnum pe : PriorityEnum.values()) {
      if (pe.priority == number) {
        return pe;
      }
    }
    return NON_EXIST;
  }
}
