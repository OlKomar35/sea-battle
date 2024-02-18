package org.komar.technical_project.client.gamespace;

import java.util.LinkedHashMap;
import java.util.Map;

public class SetOfShips {

  //region Поля класса SetOfShip
  protected Map<Ship, Integer> completeSetOfShips;
  protected int totalCount = 0;
  protected int maxShipElements = 0;
  //endregion

  /**
   * Класс хранит в себя все данные о наборе кораблей
   */
  public SetOfShips() {
    completeSetOfShips = new LinkedHashMap<>();
    restoreCompleteSet();

    for (Map.Entry<Ship, Integer> entry : completeSetOfShips.entrySet()) {
      totalCount += entry.getValue();
      maxShipElements +=(entry.getKey().getLengthShip()*entry.getValue());
    }
  }

  public Map<Ship, Integer> getCompleteSetOfShips() {
    return completeSetOfShips;
  }

  /**
   * Общее количество кораблей
   * @return кол-во караблей
   */
  public int getTotalShipsCount() {
    return totalCount;
  }

  /**
   * Удаляет из общего набора кораблей "убитый корабль"
   * @param ship корабль, который был "убит"
   */
  public void removeShips(Ship ship) {
    if (this.completeSetOfShips.get(ship) > 0) {
      int oldValue = completeSetOfShips.get(ship);
      completeSetOfShips.put(ship, oldValue - 1);
    }
  }

  /**
   * Отображает вид кораблей и их количество
   */
  public void viewCountShips() {
    for (Map.Entry<Ship, Integer> entry : completeSetOfShips.entrySet()) {
      System.out.println(entry.getKey().getViewShip() + " - " + entry.getValue() + " штук");
    }
  }

  /**
   * Выдает общее количество элементов всех кораблей на поле
   * @return общее кол-во элементов
   */
  public int getMaxShipElements() {
    return maxShipElements;
  }

  public void restoreCompleteSet(){
    completeSetOfShips.put(Ship.SIX, Ship.SIX.getMaxCountShip());
    completeSetOfShips.put(Ship.FIVE, Ship.FIVE.getMaxCountShip());
    completeSetOfShips.put(Ship.FOUR, Ship.FOUR.getMaxCountShip());
    completeSetOfShips.put(Ship.THREE, Ship.THREE.getMaxCountShip());
    completeSetOfShips.put(Ship.TWO, Ship.TWO.getMaxCountShip());
    completeSetOfShips.put(Ship.ONE, Ship.ONE.getMaxCountShip());
  }
}
