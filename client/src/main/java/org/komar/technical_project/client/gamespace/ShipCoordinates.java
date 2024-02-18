package org.komar.technical_project.client.gamespace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ShipCoordinates {

  //region Поля класса ShipCoordinates
  private Map<Coordinates, GameElements> shipCoordinates = new HashMap();
  private List<Coordinates> coordinatesList = new ArrayList<>();
  private Coordinates head;
  private Object[][] playersGameBord;
  //endregion

  /**
   * Класс для хранения информации о подбитых кораблях
   * @param row номер строки "головы" раненого корабля
   * @param column номер столбца "головы" раненого корабля
   * @param playersGameBord игровое поле игрока
   * ("Голова" - это первый раненый элемент в корабле,
   *   "Хвост" - последний раненый элемент)
   */
  public ShipCoordinates(int row,
                         int column,
                         Object[][] playersGameBord) {
    this.playersGameBord = playersGameBord;
    this.head = new Coordinates(row, column);
    this.coordinatesList.add(head);
    shipCoordinates.put(head, GameElements.HURT);

    boolean flag = true;
    int count = 0;
    do {
      flag = fillMap(coordinatesList.get(count));
      for (Map.Entry<Coordinates, GameElements> entry : shipCoordinates.entrySet()) {
        if (!coordinatesList.contains(entry.getKey())) {
          coordinatesList.add(entry.getKey());
        }
      }
      count++;
    } while (flag || count < coordinatesList.size());
  }

  private boolean fillMap(Coordinates coordinates) {
    int rows = playersGameBord.length;
    int cols = playersGameBord[0].length;
    int row = coordinates.getRow();
    int column = coordinates.getColumn();
    boolean flag = false;

    for (int i = row - 1; i <= row + 1; i++) {
      for (int j = column - 1; j <= column + 1; j++) {
        if (i >= 0 && i < rows && j >= 0 && j < cols && (i != row || j != column)) {
          if (playersGameBord[i][j].equals(GameElements.ONE_ELEMENT.getNameElement())) {
            if (!shipCoordinates.containsKey(new Coordinates(i, j))) {
              shipCoordinates.put(new Coordinates(i, j), GameElements.ONE_ELEMENT);
              flag = true;
            }
          }
        }
      }
    }
    return flag;
  }

  public boolean checkSurroundingElements(int row,
                                          int col) {
    boolean isChecked = false; //KILLED
    for (Map.Entry<Coordinates, GameElements> entry : shipCoordinates.entrySet()) {
      if (entry.getValue().equals(GameElements.ONE_ELEMENT)) {
        isChecked = true; //HURT
      }
    }
    return isChecked;
  }

  public Map<Coordinates, GameElements> getShipCoordinates() {
    return shipCoordinates;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("Корабль-" + shipCoordinates.size() + " паулбный\n");
    for (Map.Entry<Coordinates, GameElements> entry : shipCoordinates.entrySet()) {
      sb.append(entry.getKey().getRow() + "-" + entry.getKey().getColumn() + "\n");
    }
    return sb.toString();
  }
}
