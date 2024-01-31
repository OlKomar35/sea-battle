package org.komar.technical_project.gamers;

import java.util.Random;
import java.util.Scanner;
import org.komar.technical_project.gamespace.Coordinates;
import org.komar.technical_project.gamespace.Orientation;
import org.komar.technical_project.gamespace.WreckedShip;
import org.komar.technical_project.helper.GameElements;

public class Bot extends Player {

  private int randomCount;
  private String[][] opponentShipsMatrix;
  private WreckedShip wreckedShip;
  private int row;
  private int column;
  int countHurt;

  public Bot() {
    super("Bot Валера");
    gameField.randomFillGameField(setOfShips.getCompleteSetOfShips());

    this.randomCount = 0;
    this.opponentShipsMatrix = new String[getGameField().getROW_COUNT()][gameField.getCOLUMN_COUNT()];

    this.wreckedShip = new WreckedShip();
    this.row = -1;
    this.column = -1;
    this.countHurt = 0;
  }


  @Override
  public Coordinates getCoordinates(Scanner scanner) {
    char columnChar = 0;
    boolean isAlready = false;

    if (randomCount <= 10) {

      if (status.equals(GameElements.MISSED) || status.equals(GameElements.KILLED)) {
        if (countHurt > 0) {
          countHurt--;
        }
        while (!isAlready) {
          Random random = new Random();
          int randomRowIndex = random.nextInt(gameField.getRowsNameList().length);
          int randomColumnIndex = random.nextInt(gameField.getColumnsNameList().size());
          this.row = gameField.getRowsNameList()[randomRowIndex];
          columnChar = gameField.getColumnsNameList().get(randomColumnIndex);
          this.column = getGameField().getColumnsNameList().indexOf(columnChar);
          randomCount++;
          if (opponentShipsMatrix[row - 1][column] == null) {
            isAlready = true;
            opponentShipsMatrix[row - 1][column] = BUSY;
          }
        }
      } else if (status.equals(GameElements.HURT)) {
        System.out.println(countHurt);
        Orientation orientation = Orientation.NONE;
        Coordinates oldCoordinates = new Coordinates();
        oldCoordinates.setRow(row - 1);
        oldCoordinates.setColumn(column);
        wreckedShip.getWreckedShipCoordinates().add(oldCoordinates);
        if (wreckedShip.getWreckedShipCoordinates().size() == 2) {
          if (wreckedShip.getWreckedShipCoordinates().get(0).getRow() -
              wreckedShip.getWreckedShipCoordinates().get(1).getRow() == 0) {
            orientation = Orientation.HORIZONTAL;
          } else if (wreckedShip.getWreckedShipCoordinates().get(0).getColumn() -
              wreckedShip.getWreckedShipCoordinates().get(1).getColumn() == 0) {
            orientation = Orientation.VERTICAL;
          }
        }
        if (countHurt == 0) {
          countHurt++;
          Coordinates coordinates = createRandomCoordinate(row, column);
          row = coordinates.getRow();
          column = coordinates.getColumn();
          columnChar = gameField.getColumnsNameList().get(column);
        } else {
          if (orientation.equals(Orientation.VERTICAL)) {
            //todo проверки что не выходит за пределы и проверки в верх или вниз
            column++;
            column--;
          } else if (orientation.equals(Orientation.HORIZONTAL)){
            //todo проверки что не выходит за пределы и проверки в верх или вниз
            row++;
            row--;
          }

        }
      }
    }

    System.out.println(row + "-" + columnChar);
    return new Coordinates(row, column);
  }

  public Coordinates createRandomCoordinate(int row,
                                            int col) {
    boolean flag = false;
    while (!flag) {
      Random random = new Random();
      int choice = random.nextInt(4);

      switch (choice) {
        case 0:
          row = row - 1;
          break;
        case 1:
          row = row + 1;
          break;
        case 2:
          col = col - 1;
          break;
        case 3:
          col = col + 1;
          break;
      }
      if ((row > 0 && row <= 16) && (col >= 0 && col < 16)) {
        flag = true;
      }
    }

    return new Coordinates(row, col);

  }
}
