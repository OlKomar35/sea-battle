package org.komar.technical_project.client.gamespace;

public enum Direction {
  NONE("None", 0, 0, -1),
  LEFT("Left", 0, -1, 0),
  RIGHT("Right", 0, 1, 1),
  TOP("Left", -1, 0, 2),
  BOTTOM("Left", 1, 0, 3);

  private final String direction;
  private final int row;
  private final int column;
  private final int randomNum;

  Direction(String direction,
            int row,
            int column,
            int randomNum) {
    this.direction = direction;
    this.row = row;
    this.column = column;
    this.randomNum = randomNum;
  }

  public String getDirection() {
    return direction;
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
