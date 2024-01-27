package org.komar.technical_project.game;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.komar.technical_project.helper.ConsoleHelper;
import org.komar.technical_project.helper.GameElements;
import org.komar.technical_project.helper.TextColor;

public class GameField {

  /**
   * Данный класс необходим для отрисовки и заполнения игрового поля
   */

  private final String ELEMENT_SHIP = Ship.ONE_ELEMENT.getViewShip();
  private final String MISSED = GameElements.MISSED.getNameElement();
  private final String KILLED = GameElements.KILLED.getNameElement();
  private final String BUSY = GameElements.BUSY.getNameElement();
  private final int ROW_COUNT = 16;
  private final int COLUMN_COUNT = 16;

  private final int[] rowsNameList = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
  private final List<Character> columnsNameList
      = Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p');

  private String[][] gameFieldMatrix;
  private boolean stepPlayer;

  public GameField() {
    this.gameFieldMatrix = new String[ROW_COUNT][COLUMN_COUNT];
    this.stepPlayer = true;
  }

  public boolean isStepPlayer() {
    return stepPlayer;
  }

  public boolean isBusyGameFieldCells(int lengthShip,
                                      int rowCoordinate,
                                      char columnCoordinateChar,
                                      String orientation) {
    boolean isFreeCells = true;
    int columnCoordinate = columnsNameList.indexOf(columnCoordinateChar);
    if (orientation.equals("v")) {

      if ((rowCoordinate - 1) > 0 && (rowCoordinate - 1 + lengthShip - 1) < ROW_COUNT) {
        for (int r = rowCoordinate - 1; r <= rowCoordinate - 1 + lengthShip - 1; r++) {
          if (gameFieldMatrix[r][columnCoordinate] != null) {
            isFreeCells = false;
            break;
          }
        }
      } else {
        isFreeCells = false;
      }
    }
    if (orientation.equals("h")) {
      if (columnCoordinate > 0 && (columnCoordinate + lengthShip - 1 < COLUMN_COUNT)) {
        for (int c = columnCoordinate; c <= columnCoordinate + lengthShip - 1; c++) {
          if (gameFieldMatrix[rowCoordinate - 1][c] != null) {
            isFreeCells = false;
            break;
          }
        }
      } else {
        isFreeCells = false;
      }
    }
    return isFreeCells;
  }

  public void fillGameField(int lengthShip,
                            int rowCoordinate,
                            char columnCoordinateChar,
                            String orientation) {
    int columnCoordinate = columnsNameList.indexOf(columnCoordinateChar);
    if (orientation.equals("v")) {
      for (int count = 0; count < lengthShip; count++) {
        gameFieldMatrix[rowCoordinate - 1 + count][columnCoordinate] = ELEMENT_SHIP;
      }
      int firstRow = Math.max(0, rowCoordinate - 2);
      int lastRow = Math.min(ROW_COUNT - 1, rowCoordinate - 1 + lengthShip);

      int firstCol = Math.max(0, columnCoordinate - 1);
      int lastCol = Math.min(COLUMN_COUNT - 1, columnCoordinate + 1);

      for (int r = firstRow; r <= lastRow; r++) {
        for (int c = firstCol; c <= lastCol; c++) {
          if (gameFieldMatrix[r][c] == null) {
            gameFieldMatrix[r][c] = BUSY;
          }
        }
      }
    } else if (orientation.equals("h")) {
      for (int count = 0; count < lengthShip; count++) {
        gameFieldMatrix[rowCoordinate - 1][columnCoordinate + count] = ELEMENT_SHIP;
      }
      int firstRow = Math.max(0, rowCoordinate - 2);
      int lastRow = Math.min(ROW_COUNT - 1, rowCoordinate);

      int firstCol = Math.max(0, columnCoordinate - 1);
      int lastCol = Math.min(COLUMN_COUNT - 1, columnCoordinate + lengthShip);
      for (int r = firstRow; r <= lastRow; r++) {
        for (int c = firstCol; c <= lastCol; c++) {
          if (gameFieldMatrix[r][c] == null) {
            gameFieldMatrix[r][c] = BUSY;
          }
        }
      }
    }
  }

  public void viewGameBoard() {
    System.out.print("    ");
    for (char c : columnsNameList) {
      System.out.print(TextColor.ANSI_BLUE.getColorText() + c + " ");
    }
    System.out.print("     ");
    for (char c : columnsNameList) {
      System.out.print(TextColor.ANSI_BLUE.getColorText() + c + " ");
    }
    System.out.println();
    for (int row = 0; row < rowsNameList.length; row++) {
      if (row < 9) {
        System.out.print(" " + rowsNameList[row] + "  ");
      } else {
        System.out.print(" " + rowsNameList[row] + " ");
      }
      for (int column = 0; column < gameFieldMatrix[0].length; column++) {
        if (gameFieldMatrix[row][column] != null && !gameFieldMatrix[row][column].equals(BUSY)) {
          System.out.print(gameFieldMatrix[row][column] + " ");
        } else {
          System.out.print("~ ");
        }
      }
      System.out.println();
    }
    System.out.println(TextColor.ANSI_RESET.getColorText());
  }

  public void randomFillGameField(Map<Ship, Integer> completeSetOfShips) {

    Random random = new Random();
    for (Map.Entry<Ship, Integer> ship : completeSetOfShips.entrySet()) {
      for (int count = 1; count <= ship.getValue(); count++) {
        boolean isView = false;
        while (!isView) {
          int randomRowIndex = random.nextInt(rowsNameList.length); // Генерация случайного индекса строки
          int randomColumnIndex = random.nextInt(columnsNameList.size()); // Генерация случайного индекса столбца
          int row = rowsNameList[randomRowIndex];
          char column = columnsNameList.get(randomColumnIndex);
          int randomValue = random.nextInt(2); // Генерация случайного числа 0 или 1
          String orientation =
              randomValue == 0 ? "v" : "h"; // Возвращение "v" если случайное число равно 0, иначе возвращение "h"

          if (isBusyGameFieldCells(ship.getKey().getLengthShip(), row, column, orientation)) {
            fillGameField(ship.getKey().getLengthShip(), row, column, orientation);
            isView = true;
          }
        }
      }
    }
//    viewGameBoard();
  }

  public void playerWalks(int row,
                          int column) {
    if (gameFieldMatrix[row][column] == null && gameFieldMatrix[row][column].equals(BUSY)) {
      ConsoleHelper.getMsgMissed();
      gameFieldMatrix[row][column] = MISSED;
      stepPlayer = false;
    } else if(gameFieldMatrix[row][column].equals(ELEMENT_SHIP)){
      ConsoleHelper.getMsgKilled();
      gameFieldMatrix[row][column] = KILLED;
      stepPlayer = true;
    } else if (gameFieldMatrix[row][column].equals(MISSED) ||gameFieldMatrix[row][column].equals(KILLED)) {
      ConsoleHelper.getMsgLoser();
      stepPlayer = false;
    }
  }

  public int[] getRowsNameList() {
    return rowsNameList;
  }

  public List<Character> getColumnsNameList() {
    return columnsNameList;
  }

  public String[][] getGameFieldMatrix() {
    return gameFieldMatrix;
  }

}
