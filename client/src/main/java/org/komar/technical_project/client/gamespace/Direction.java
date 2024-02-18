package org.komar.technical_project.client.gamespace;

/**
 * Класс-перечисление для хранения всех возможных направлений для обстрела
 */
public enum Direction {
  NONE("None", 0, 0, -1),
  LEFT("Left", 0, -1, 0),
  RIGHT("Right", 0, 1, 1),
  TOP("Top", -1, 0, 2),
  BOTTOM("Bottom", 1, 0, 3);

  private final String direction;
  private final int row;
  private final int column;
  private final int randomNum;

  /**
   * Класс-перечисление для хранения всех возможных направлений для обстрела
   * @param direction направление(лево, право, верх, низ)
   * @param row значение сдвига по строке
   * @param column значение сдвига по столбцу
   * @param randomNum числовое значение для направления
   */
  Direction(String direction,
            int row,
            int column,
            int randomNum) {
    this.direction = direction;
    this.row = row;
    this.column = column;
    this.randomNum = randomNum;
  }

  public int getRow() {
    return row;
  }

  public int getColumn() {
    return column;
  }

  public int getRandomNum() {
    return randomNum;
  }
}
