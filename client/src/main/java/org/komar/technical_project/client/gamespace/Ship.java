package org.komar.technical_project.client.gamespace;

/**
 * Перечисление всех видов кораблей(их вид и кол-во элементов)
 */
public enum Ship {
  SIX("@ @ @ @ @ @", 6),
  FIVE("@ @ @ @ @", 5),
  FOUR("@ @ @ @", 4),
  THREE("@ @ @", 3),
  TWO("@ @", 2),
  ONE("@", 1);

  private final String viewShip;
  private final int lengthShip;

  /**
   * Перечисление всех видов кораблей
   *
   * @param viewShip   вид корабля
   * @param lengthShip длина корабля
   */
  Ship(String viewShip,
       int lengthShip) {
    this.viewShip = viewShip;
    this.lengthShip = lengthShip;
  }

  public String getViewShip() {
    return this.viewShip;
  }

  /**
   * Метод для получения корабля по его длине
   *
   * @param lengthShip длин корабля
   * @return корабль, который соответствует введенной длине
   */
  public static Ship getViewShipByLength(int lengthShip) {
    for (Ship ship : Ship.values()) {
      if (ship.getLengthShip() == lengthShip) {
        return ship;
      }
    }
    return null;
  }

  public int getLengthShip() {
    return lengthShip;
  }

  /**
   * Подсчитывает максимальное количество кораблей определенного вида, которое необходимо
   * расставить на игровом поле
   * @return максимальное число кораблей данного вида
   */
  public int getMaxCountShip() {
    return 7 - lengthShip;
  }
}
