package org.komar.technical_project.client.gamers;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import org.komar.technical_project.client.gamespace.Orientation;
import org.komar.technical_project.client.gamespace.Ship;
import org.komar.technical_project.client.helper.ConsoleHelper;
import org.komar.technical_project.client.helper.TextColor;

public class Human extends Player {

  private final Scanner scanner;
  private boolean flagShip;



  /**
   * Класс, который реализовывает логику для человека
   *
   * @param name    имя игрока
   * @param scanner сканер, для чтения данных из консоли
   */

  public Human(String name,
               Scanner scanner) {
    super();
    this.name = name;
    this.scanner = scanner;
    this.flagShip = false;

    System.out.println(name + ", заполните ваши игровые поля кораблями\n");

    ConsoleHelper.getMsgFillGameField();

    int totalCountShip = setOfShips.getTotalShipsCount();
    while (totalCountShip > 0) {
      while (!flagShip) {
        System.out.printf("Осталось ввести %d корабль(корабля)\n", totalCountShip);
        System.out.println("Введите команду ship с необходимыми координатами");
        String coordinatesShipMsg = scanner.nextLine();

        if (coordinatesShipMsg.startsWith("ship ")) {

          String[] partMsg = coordinatesShipMsg.split(" -");
          String msg = partMsg[1];

          if (msg.equals("p")) {

            try {
              int lengthShip = Integer.parseInt(partMsg[2]);
              Ship selectedShip = Ship.getViewShipByLength(lengthShip);
              int rowCoordinate = Integer.parseInt(partMsg[3]);
              char columnCoordinateChar = partMsg[4].charAt(0);
              String orientation = partMsg[5];
              String orientationName = Orientation.getNameByAbbreviation(orientation);

              if (setOfShips.getCompleteSetOfShips().get(selectedShip) != 0) {
                if (gameField
                    .isBusyGameFieldCells(lengthShip, rowCoordinate, columnCoordinateChar, orientation)) {
                  gameField.fillGameField(lengthShip, rowCoordinate, columnCoordinateChar, orientation);
                  ConsoleHelper.clearConsole();

                  setOfShips.removeShips(Ship.getViewShipByLength(lengthShip));
                  totalCountShip--;
                } else {
                  System.out.println("Не возможно разместить корабль длиной " + lengthShip + " начиная с ячейки ["
                                         + rowCoordinate + "-" + columnCoordinateChar + "]" + "-" + orientationName);
                }
              } else {
                System.out.println("Корабли с длиной " + lengthShip + " уже расставлены");
              }
              showPlayerGameField();
              flagShip = true;
            } catch (ArrayIndexOutOfBoundsException | NumberFormatException e ) {
              ConsoleHelper.getMsgInvalidCommandEntered();
              flagShip = false;
            }
          } else if (msg.equals("r")) {
            gameField.randomFillGameField(setOfShips.getCompleteSetOfShips());
            ConsoleHelper.clearConsole();
            totalCountShip = 0;
            setOfShips.restoreCompleteSet();
            showPlayerGameField();
            flagShip = true;
          } else {
            ConsoleHelper.getMsgInvalidCommandEntered();
            flagShip = false;
          }

        } else {
          ConsoleHelper.getMsgInvalidCommandEntered();
        }
      }
    }
  }

  /**
   * Отрисовывает игровое поле только данного игрока
   */
  private void showPlayerGameField() {
    System.out.print("    ");
    for (char c : this.getGameField().getColumnsNameList()) {
      System.out.print(TextColor.ANSI_PURPLE.getColorText() + c + " ");
    }
    System.out.println();
    for (int row = 0; row < this.getGameField().getRowsNameList().length; row++) {
      if (row < 9) {
        System.out.print(" " + this.getGameField().getRowsNameList()[row] + "  ");
      } else {
        System.out.print(" " + this.getGameField().getRowsNameList()[row] + " ");
      }
      for (int column = 0; column < this.getGameField().getGameFieldMatrix()[0].length; column++) {
        if (this.getGameField().getGameFieldMatrix()[row][column] != null
            && !this.getGameField().getGameFieldMatrix()[row][column].equals(BUSY)) {
          System.out.print(this.getGameField().getGameFieldMatrix()[row][column] + " ");
        } else {
          System.out.print("~ ");
        }
      }
      System.out.println();
    }
    System.out.println();

    System.out.println("  Ваши корабли, " + this.getName() + " ");

    for (Entry<Ship, Integer> entry1 : this.getSetOfShips().getCompleteSetOfShips().entrySet()) {
      System.out.print(entry1.getKey().getViewShip() + " - " + entry1.getValue() + " штук");
      System.out.println();
    }
    System.out.println(TextColor.ANSI_RESET.getColorText());
  }
}
