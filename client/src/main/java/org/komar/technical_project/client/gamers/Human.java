package org.komar.technical_project.client.gamers;

import java.util.Scanner;
import org.komar.technical_project.client.gamespace.Ship;
import org.komar.technical_project.client.helper.ConsoleHelper;

public class Human extends Player {

  private final Scanner scanner;

  public Human(String name,
               Scanner scanner) {
    super();
    this.name = name;
    this.scanner = scanner;

    System.out.println(name+", заполните ваши игровые поля кораблями\n");

    ConsoleHelper.getMsgFillGameField();

    String coordinatesShipMsg = scanner.nextLine();

    if (coordinatesShipMsg.startsWith("ship")) {
      int totalCountShip = setOfShips.getTotalShipsCount();
      String[] partMsg = coordinatesShipMsg.split(" -");
      String msg = partMsg[1];

      if (msg.equals("p")) {
        while (totalCountShip > 0) {
          System.out.printf("Осталось ввести %d корабль(корабля)\n", totalCountShip);
          System.out.println("Введите команду ship с необходимыми координатами");

          int lengthShip = Integer.parseInt(partMsg[2]);
          int rowCoordinate = Integer.parseInt(partMsg[3]);
          char columnCoordinateChar = partMsg[4].charAt(0);
          String orientation = partMsg[5];

          if (gameField
              .isBusyGameFieldCells(lengthShip, rowCoordinate, columnCoordinateChar, orientation)) {
            gameField.fillGameField(lengthShip, rowCoordinate, columnCoordinateChar, orientation);
            ConsoleHelper.clearConsole();

            setOfShips.removeShips(Ship.getViewShipByLength(lengthShip));
            setOfShips.viewCountShips();
            totalCountShip--;
          }
        }
      } else if (msg.equals("r")) {
        gameField.randomFillGameField(setOfShips.getCompleteSetOfShips());
        ConsoleHelper.clearConsole();
      }
    } else {
      ConsoleHelper.getMsgInvalidCommandEntered();
    }
  }

}
