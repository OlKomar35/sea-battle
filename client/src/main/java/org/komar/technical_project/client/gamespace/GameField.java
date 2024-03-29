package org.komar.technical_project.client.gamespace;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GameField {

  //region Поля класса GameField
  private final String ELEMENT_SHIP = GameElements.ONE_ELEMENT.getNameElement();
  private final String BUSY = GameElements.BUSY.getNameElement();
  private final int ROW_COUNT = 16;
  private final int COLUMN_COUNT = 16;
  private final int[] rowsNameList = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
  private final List<Character> columnsNameList
      = Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p');
  private Object[][] gameFieldMatrix;
  //endregion

  /**
   * Данный класс необходим для отрисовки и заполнения игрового поля
   */
  public GameField() {
    this.gameFieldMatrix = new String[ROW_COUNT][COLUMN_COUNT];
  }

  //region Геттеры класса
  public int getColumnCoordinate(char value) {
    return columnsNameList.indexOf(value);
  }

  public int[] getRowsNameList() {
    return rowsNameList;
  }

  public List<Character> getColumnsNameList() {
    return columnsNameList;
  }

  public Object[][] getGameFieldMatrix() {
    return gameFieldMatrix;
  }

  public int getROW_COUNT() {
    return ROW_COUNT;
  }

  public int getCOLUMN_COUNT() {
    return COLUMN_COUNT;
  }
  //endregion

  /**
   * Данный метод проверяет правильность расстановки корабля, проверяет ли заняты ячейки с введенными координатами,
   * куда пытаются поставить корабль
   *
   * @param lengthShip           длина корабля (кол-во элементов)
   * @param rowCoordinate        координата строки
   * @param columnCoordinateChar координата столбца
   * @param orientation          ориентация корабля
   * @return вернет true если начиная с клетки с координатами [rowCoordinate-columnCoordinateChar] можно поставить
   * корабль длиной lengthShip клеток учитывая ориентацию корабля. В противном случае вернет false.
   */
  public boolean isBusyGameFieldCells(int lengthShip,
                                      int rowCoordinate,
                                      char columnCoordinateChar,
                                      String orientation) {
    boolean isFreeCells = true;
    int columnCoordinate = getColumnCoordinate(columnCoordinateChar);
    if (orientation.equals(Orientation.VERTICAL.getOrientation())) {

      if ((rowCoordinate - 1) >= 0 && (rowCoordinate - 1 + lengthShip - 1) < ROW_COUNT) {
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
    if (orientation.equals(Orientation.HORIZONTAL.getOrientation())) {
      if (columnCoordinate >=0  && (columnCoordinate + lengthShip - 1 < COLUMN_COUNT)) {
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

  /**
   * Заполнение поля кораблями
   * @param lengthShip           длина корабля
   * @param rowCoordinate        координата строки для начала корабля
   * @param columnCoordinateChar координата столбца для начала корабля
   * @param orientation          ориентация корабля (горизонтально располагать или вертикально)
   */
  public void fillGameField(int lengthShip,
                            int rowCoordinate,
                            char columnCoordinateChar,
                            String orientation) {
    int columnCoordinate = getColumnCoordinate(columnCoordinateChar);
    if (orientation.equals(Orientation.VERTICAL.getOrientation())) {
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
    } else if (orientation.equals(Orientation.HORIZONTAL.getOrientation())) {
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

  /**
   * Записывает матрицу игрока в файл
   * @param writer буфер для записи файла
   * @throws IOException исключение, которое может возникнуть при записи в файл
   */
  public void writeToFileGameBoard(BufferedWriter writer) throws IOException {
    writer.newLine();
    writer.write("    ");
    for (char c : columnsNameList) {
      writer.write(c + " ");
    }
    writer.newLine();
    for (int row = 0; row < rowsNameList.length; row++) {
      if (row < 9) {
        writer.write("_" + rowsNameList[row] + "  ");
      } else {
        writer.write("_" + rowsNameList[row] + " ");
      }
      for (int column = 0; column < gameFieldMatrix[0].length; column++) {
        if (gameFieldMatrix[row][column] != null && !gameFieldMatrix[row][column].equals(BUSY)) {
          writer.write(gameFieldMatrix[row][column] + " ");
        } else {
          writer.write("~ ");
        }
      }
      writer.newLine();
    }
  }

  /**
   * Рандомное заполнение поля кораблями
   * @param completeSetOfShips набор всех кораблей которые необходимо расставить для игры
   */
  public void randomFillGameField(Map<Ship, Integer> completeSetOfShips) {

    Random random = new Random();
    for (Map.Entry<Ship, Integer> ship : completeSetOfShips.entrySet()) {
      for (int count = 1; count <= ship.getValue(); count++) {
        boolean isView = false;
        while (!isView) {
          int randomRowIndex = random.nextInt(rowsNameList.length);
          int randomColumnIndex = random.nextInt(columnsNameList.size());
          int row = rowsNameList[randomRowIndex];
          char column = columnsNameList.get(randomColumnIndex);
          int randomValue = random.nextInt(2);
          Orientation orientation =
              randomValue == 0 ? Orientation.VERTICAL : Orientation.HORIZONTAL;

          if (isBusyGameFieldCells(ship.getKey().getLengthShip(), row, column, orientation.getOrientation())) {
            fillGameField(ship.getKey().getLengthShip(), row, column, orientation.getOrientation());
            isView = true;
          }
        }
      }
    }
  }
}
