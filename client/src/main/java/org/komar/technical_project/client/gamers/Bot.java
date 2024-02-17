package org.komar.technical_project.client.gamers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import org.komar.technical_project.client.gamespace.Coordinates;
import org.komar.technical_project.client.gamespace.Direction;
import org.komar.technical_project.client.gamespace.Orientation;
import org.komar.technical_project.client.gamespace.Ship;
import org.komar.technical_project.client.gamespace.ShipCoordinates;
import org.komar.technical_project.client.helper.GameElements;

public class Bot extends Player {

  private int hurtShipElements;
  private Object[][] opponentShipsMatrix;
  private int row;
  private int column;
  private int countHurt;
  private List<Coordinates> listForTwoCoordinates;
  private Orientation orientation;
  private Direction direction;
  private boolean isKilled;
  private final int MAX_SHIP_ELEMENTS = setOfShips.getMaxShipElements();

  public Bot() {
    super();
    this.name = "Бот Валерьян";
    gameField.randomFillGameField(setOfShips.getCompleteSetOfShips());

    this.hurtShipElements = 0;
    this.opponentShipsMatrix = new String[getGameField().getROW_COUNT()][gameField.getCOLUMN_COUNT()];

    this.row = -1;
    this.column = -1;
    this.countHurt = 0;
    this.ship = new ShipCoordinates();
    this.isKilled = true;
    this.listForTwoCoordinates = new ArrayList<>();
    this.orientation = Orientation.NONE;
    this.direction = Direction.NONE;
  }

  /**
   * Метод для получения координат, для Бота.
   *
   * @param scanner переменная для чтения данных из консоли
   * @return
   */
  @Override
  public Coordinates getCoordinates(Scanner scanner) {
    char columnChar = 0;
    boolean isAlready = false;

    if (hurtShipElements <= MAX_SHIP_ELEMENTS / 2 && status.equals(GameElements.MISSED) && (isKilled || countHurt == 0)
        || status.equals(GameElements.KILLED)) {

      if (status.equals(GameElements.KILLED)) {
        opponentShipsMatrix[row - 1][column] = KILLED;
        fillGameFieldOpponent();
        listForTwoCoordinates.clear();
        isKilled = true;
        countHurt = 0;
        hurtShipElements++;
      }
      while (!isAlready) {
        Random random = new Random();
        int randomRowIndex = random.nextInt(gameField.getRowsNameList().length);
        int randomColumnIndex = random.nextInt(gameField.getColumnsNameList().size());
        this.row = gameField.getRowsNameList()[randomRowIndex];
        columnChar = gameField.getColumnsNameList().get(randomColumnIndex);
        this.column = getGameField().getColumnsNameList().indexOf(columnChar);
        if (opponentShipsMatrix[row - 1][column] == null) { // заполняем матрицу выстрелами, чтобы не повторялись
          isAlready = true;
          opponentShipsMatrix[row - 1][column] = BUSY;
        }
      }
    } else if (status.equals(GameElements.HURT)) {
      countHurt++;
      hurtShipElements++;
      isKilled = false;
      Coordinates oldCoordinates = new Coordinates();
      oldCoordinates.setRow(row);
      oldCoordinates.setColumn(column);
      opponentShipsMatrix[oldCoordinates.getRow() - 1][oldCoordinates.getColumn()] = KILLED;

      if (countHurt == 1) {
        listForTwoCoordinates.add(oldCoordinates);
        orientation = Orientation.NONE;
      } else if (countHurt == 2) {
        listForTwoCoordinates.add(oldCoordinates);
        if (listForTwoCoordinates.get(0).getRow() - listForTwoCoordinates.get(1).getRow() == 0) {
          orientation = Orientation.HORIZONTAL;
        } else if (listForTwoCoordinates.get(0).getColumn() - listForTwoCoordinates.get(1).getColumn() == 0) {
          orientation = Orientation.VERTICAL;
        }
      } else {
        listForTwoCoordinates.add(oldCoordinates);
      }
      while (!isAlready) {
        Coordinates coordinates = createRandomCoordinate(listForTwoCoordinates.get(countHurt - 1).getRow(),
                                                         listForTwoCoordinates.get(countHurt - 1).getColumn(),
                                                         orientation, direction);
        row = coordinates.getRow();
        column = coordinates.getColumn();
        if (row == 0 || column == -1) {
          direction = changedDirection(orientation, direction);
          changedHeadShip();
        } else if (opponentShipsMatrix[row - 1][column] == null) {
          isAlready = true;
          opponentShipsMatrix[row - 1][column] = BUSY;
        } else if (!direction.equals(Direction.NONE)) {
          direction = changedDirection(orientation, direction);
          changedHeadShip();
        }
      }
    } else if ((status.equals(GameElements.MISSED) && !isKilled && countHurt > 0)) {

      direction = changedDirection(orientation, direction);
      changedHeadShip();

      while (!isAlready) {
        Coordinates coordinates = createRandomCoordinate(listForTwoCoordinates.get(countHurt - 1).getRow(),
                                                         listForTwoCoordinates.get(countHurt - 1).getColumn(),
                                                         orientation, direction);
        row = coordinates.getRow();
        column = coordinates.getColumn();
        if (opponentShipsMatrix[row - 1][column] == null) {
          isAlready = true;
          opponentShipsMatrix[row - 1][column] = BUSY;
        }
      }
    } else {
      for (Map.Entry<Ship, Integer> entry : setOfShips.getCompleteSetOfShips().entrySet()) {
        System.out.println(entry.getKey() + " " + entry.getValue());
      }
    }

    columnChar = gameField.getColumnsNameList().get(column);
    System.out.println(row + "-" + columnChar);
    return new Coordinates(row, column);
  }

  private void changedHeadShip() {
    if (countHurt >= 2) {
      Coordinates temp = listForTwoCoordinates.get(0);
      listForTwoCoordinates.set(0, listForTwoCoordinates.get(listForTwoCoordinates.size() - 1));
      listForTwoCoordinates.set(listForTwoCoordinates.size() - 1, temp);
    }
  }

  private Direction changedDirection(Orientation orientation,
                                     Direction direction) {

    if (orientation.equals(Orientation.HORIZONTAL) && direction.equals(Direction.LEFT)) {
      return Direction.RIGHT;
    } else if (orientation.equals(Orientation.HORIZONTAL) && direction.equals(Direction.RIGHT)) {
      return Direction.LEFT;
    } else if (orientation.equals(Orientation.VERTICAL) && direction.equals(Direction.TOP)) {
      return Direction.BOTTOM;
    } else if (orientation.equals(Orientation.VERTICAL) && direction.equals(Direction.BOTTOM)) {
      return Direction.TOP;
    }
    return Direction.NONE;
  }

  public Coordinates createRandomCoordinate(int row,
                                            int col,
                                            Orientation orientation,
                                            Direction direction) {
    int rowRandom = 0;
    int colRandom = -1;
    Direction dir = direction;
    if ((orientation.equals(Orientation.VERTICAL) && direction.equals(Direction.TOP) && row == 1)
        || (orientation.equals(Orientation.VERTICAL) && direction.equals(Direction.BOTTOM) && row == 16)
        || (orientation.equals(Orientation.HORIZONTAL) && direction.equals(Direction.LEFT) && col == 0)
        || (orientation.equals(Orientation.HORIZONTAL) && direction.equals(Direction.RIGHT) && col == 15)) {
      rowRandom = 0;
      colRandom = -1;
    } else {
      boolean flag = false;
      while (!flag) {
        rowRandom = row;
        colRandom = col;

        int choice = 0;
        Random random = new Random();
        if (orientation.equals(Orientation.NONE)) {
          choice = random.nextInt(4);
        } else if (orientation.equals(Orientation.HORIZONTAL)) {
          if (direction.equals(Direction.NONE)) {
            choice = random.nextInt(2);
          } else {
            choice = direction.getRandomNum();
          }
        } else if (orientation.equals(Orientation.VERTICAL)) {
          if (direction.equals(Direction.NONE)) {
            choice = random.nextInt(2) + 2;
          } else {
            choice = direction.getRandomNum();
          }
        }

        if (choice == Direction.LEFT.getRandomNum()) {
          rowRandom += Direction.LEFT.getRow();
          colRandom += Direction.LEFT.getColumn();
          dir = Direction.LEFT;
        } else if (choice == Direction.RIGHT.getRandomNum()) {
          rowRandom += Direction.RIGHT.getRow();
          colRandom += Direction.RIGHT.getColumn();
          dir = Direction.RIGHT;
        } else if (choice == Direction.TOP.getRandomNum()) {
          rowRandom += Direction.TOP.getRow();
          colRandom += Direction.TOP.getColumn();
          dir = Direction.TOP;
        } else if (choice == Direction.BOTTOM.getRandomNum()) {
          rowRandom += Direction.BOTTOM.getRow();
          colRandom += Direction.BOTTOM.getColumn();
          dir = Direction.BOTTOM;
        }

        if ((rowRandom > 0 && rowRandom <= 16) && (colRandom >= 0 && colRandom < 16)) {
          flag = true;
        } else {
          flag = false;
        }
      }

    }
    this.direction = dir;
    return new Coordinates(rowRandom, colRandom);
  }


  private void fillGameFieldOpponent() {
    int rows = opponentShipsMatrix.length;
    int cols = opponentShipsMatrix[0].length;

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (opponentShipsMatrix[i][j] != null &&
            opponentShipsMatrix[i][j].equals(KILLED)) {
          checkSurroundingElements(opponentShipsMatrix, i, j);
        }
      }
    }
  }

  public void checkSurroundingElements(Object[][] matrix,
                                       int row,
                                       int col) {
    int rows = matrix.length;
    int cols = matrix[0].length;

    for (int i = row - 1; i <= row + 1; i++) {
      for (int j = col - 1; j <= col + 1; j++) {
        if ((i >= 0 && i < rows) && (j >= 0 && j < cols)) {
          if (matrix[i][j] == null) {
            matrix[i][j] = BUSY;
          }
        }
      }
    }
  }

}
