package org.komar.technical_project.client.gamers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import org.komar.technical_project.client.gamespace.Coordinates;
import org.komar.technical_project.client.gamespace.Direction;
import org.komar.technical_project.client.gamespace.Orientation;
import org.komar.technical_project.client.gamespace.ShipCoordinates;
import org.komar.technical_project.client.helper.GameElements;

public class Bot extends Player {

  private int randomCount;
  private String[][] opponentShipsMatrix;
  private int row;
  private int column;
  private int countHurt;
  private List<Coordinates> listForTwoCoordinates;
  private Orientation orientation;
  private Direction direction;
  private boolean isKilled;

  public Bot() {
    super();
    this.name = "Бот Валера";
    gameField.randomFillGameField(setOfShips.getCompleteSetOfShips());

    this.randomCount = 0;
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


  @Override
  public Coordinates getCoordinates(Scanner scanner) {
    char columnChar = 0;
    boolean isAlready = false;

    if (status.equals(GameElements.MISSED) && (isKilled || countHurt == 0)
        || status.equals(GameElements.KILLED)) {

      if (status.equals(GameElements.KILLED)) {
        listForTwoCoordinates.clear();
        isKilled = true;
        countHurt = 0;
      }
      while (!isAlready) {
        Random random = new Random();
        int randomRowIndex = random.nextInt(gameField.getRowsNameList().length);
        int randomColumnIndex = random.nextInt(gameField.getColumnsNameList().size());
        this.row = gameField.getRowsNameList()[randomRowIndex];
        columnChar = gameField.getColumnsNameList().get(randomColumnIndex);
        this.column = getGameField().getColumnsNameList().indexOf(columnChar);
        randomCount++;
        if (opponentShipsMatrix[row - 1][column] == null) { // заполняем матрицу выстрелами, чтобы не повторялись
          isAlready = true;
          opponentShipsMatrix[row - 1][column] = BUSY;
        }
      }
    } else if (status.equals(GameElements.HURT)) {
      countHurt++;
      isKilled = false;
      Coordinates oldCoordinates = new Coordinates();
      oldCoordinates.setRow(row);
      oldCoordinates.setColumn(column);

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

        if (opponentShipsMatrix[row - 1][column] == null) {
          isAlready = true;
          opponentShipsMatrix[row - 1][column] = BUSY;
        } else if(!direction.equals(Direction.NONE)){
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
    boolean flag = false;
    int rowRandom = 0;
    int colRandom = 0;
    Direction dir = direction;
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
    this.direction = dir;
    return new Coordinates(rowRandom, colRandom);
  }
}
