package org.komar.technical_project.game;

import java.util.Arrays;
import java.util.List;
import org.komar.technical_project.helper.TextColor;

public class GameField {

  private final String ELEMENT_SHIP = Ship.ONE_ELEMENT.getViewShip();
  private final String MISSED = "\u002E";
  private final String KILLED = "X";
  private final String BUSY = "+";
  private final int ROW_COUNT = 16;
  private final int COLUMN_COUNT = 16;
  private final int[] rowsNameList = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
  private final List<Character> columnsNameList
      = Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p');

  private String[][] gameBorder;

  public GameField() {
    this.gameBorder = new String[16][16];
  }

  public String[][] getGameBorder() {
    return gameBorder;
  }

  public boolean isBusyGameFieldCells(int lengthShip,
                                      int rowCoordinate,
                                      char columnCoordinateChar,
                                      String orientation) {
    boolean isFreeCells = true;
    int columnCoordinate = columnsNameList.indexOf(columnCoordinateChar);
    if (orientation.equals("v")) {
      int firstRow = Math.max(0, rowCoordinate - 2);
      int lastRow = Math.min(15, rowCoordinate - 1 + lengthShip);

      int firstCol = Math.max(0, columnCoordinate - 1);
      int lastCol = Math.min(15, columnCoordinate + 1);

      for (int r = firstRow; r <= lastRow; r++) {
        for (int c = firstCol; c <= lastCol; c++) {
          if (gameBorder[r][c] != null) {
            isFreeCells = false;
            break;
          }
        }
      }
    }
    if (orientation.equals("h")) {
      int firstRow = Math.max(0, rowCoordinate - 2);
      int lastRow = Math.min(15, rowCoordinate);

      int firstCol = Math.max(0, columnCoordinate - 1);
      int lastCol = Math.min(15, columnCoordinate + lengthShip);

      for (int r = firstRow; r <= lastRow; r++) {
        for (int c = firstCol; c <= lastCol; c++) {
          if (gameBorder[r][c] != null) {
            isFreeCells = false;
            break;
          }
        }
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
      if (rowCoordinate - 1 >= 0) {
        gameBorder[rowCoordinate - 2][columnCoordinate - 1] = BUSY;
        gameBorder[rowCoordinate - 2][columnCoordinate] = BUSY;
        gameBorder[rowCoordinate - 2][columnCoordinate + 1] = BUSY;

        gameBorder[rowCoordinate - 1 + lengthShip][columnCoordinate - 1] = BUSY;
        gameBorder[rowCoordinate - 1 + lengthShip][columnCoordinate] = BUSY;
        gameBorder[rowCoordinate - 1 + lengthShip][columnCoordinate + 1] = BUSY;
      }
      for (int count = 0; count < lengthShip; count++) {
        if (columnCoordinate - 1 >= 0) {
          gameBorder[rowCoordinate - 1 + count][columnCoordinate - 1] = BUSY;
        }
        gameBorder[rowCoordinate - 1 + count][columnCoordinate] = ELEMENT_SHIP;
        if (columnCoordinate + 1 < 16) {
          gameBorder[rowCoordinate - 1 + count][columnCoordinate + 1] = BUSY;
        }
      }
    } else if (orientation.equals("h")) {
      for (int count = 0; count < lengthShip; count++) {
        if (rowCoordinate >= 0) {
          gameBorder[rowCoordinate - 2][columnCoordinate + count] = BUSY;
        }
        gameBorder[rowCoordinate - 1][columnCoordinate + count] = ELEMENT_SHIP;
        if (rowCoordinate < 16) {
          gameBorder[rowCoordinate][columnCoordinate + count] = BUSY;
        }
      }
    }
  }

  public void viewGameBoard() {
    System.out.print("    ");
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
      for (int column = 0; column < gameBorder[0].length; column++) {
        if (gameBorder[row][column] != null) {
          System.out.print(gameBorder[row][column] + " ");
        } else {
          System.out.print("_ ");
        }
      }
      System.out.println();
    }
    System.out.println(TextColor.ANSI_RESET.getColorText());
  }

}
