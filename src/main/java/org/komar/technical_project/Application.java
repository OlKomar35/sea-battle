package org.komar.technical_project;

import java.util.Scanner;
import org.komar.technical_project.game.Player;
import org.komar.technical_project.game.Ship;
import org.komar.technical_project.helper.TextColor;

public class Application {

  public static void main(String[] args) {

    Scanner scanner = new Scanner(System.in, "UTF-8");

    System.out.println(TextColor.ANSI_GREEN.getColorText() + "* * * * * * * * * * * * * * * * *  * * *");
    System.out.println("*              Морской бой             *");
    System.out.println("* * * * * * * * * * * * * * * * * * *  *" + TextColor.ANSI_RESET.getColorText());

    System.out.println("\n Введите ваше имя: ");
    String nameGamer1 = scanner.nextLine();
    Player player1 = new Player(nameGamer1);
    System.out.printf("Добро пожаловать, %s\n", nameGamer1);
    System.out.println("Заполните ваше игровое поле кораблями");

    player1.getGameField().viewGameBoard();
    player1.getSetOfShips().viewCountShips();

    System.out.println(TextColor.ANSI_GREEN.getColorText()
                           + "Для того чтобы заполнить поле кораблями, необходимо использовать следующую команду");
    System.out.println("ship -[размер корабля] -[координата начала расположения строка]"
                           + " -[координата начала расположения столбец] "
                           + "-[v(вертикальное расположение)/h(горизонтальное расположение)]");
    System.out.println("например: ship -5 -3 -g -v" + TextColor.ANSI_RESET.getColorText());

    int totalCountShip = player1.getSetOfShips().getTotalCount();
    while (totalCountShip > 0) {
      System.out.printf("Осталось ввести %d корабль(корабля)\n", totalCountShip);
      System.out.println("Введите команду ship с необходимыми координатами");
      String coordinatesShipMsg = scanner.nextLine();
      if (coordinatesShipMsg.startsWith("ship")) {
        String[] partMsg = coordinatesShipMsg.split(" -");

        int lengthShip = Integer.parseInt(partMsg[1]);
        int rowCoordinate = Integer.parseInt(partMsg[2]);
        char columnCoordinateChar = partMsg[3].charAt(0);
        String orientation = partMsg[4];

        if (player1.getGameField().isBusyGameFieldCells(lengthShip, rowCoordinate, columnCoordinateChar, orientation)) {
          player1.getGameField().fillGameField(lengthShip, rowCoordinate, columnCoordinateChar, orientation);
          player1.getGameField().viewGameBoard();
          player1.getSetOfShips().removeShips(Ship.getViewShipByLength(lengthShip));
          player1.getSetOfShips().viewCountShips();
          totalCountShip--;
        }
      }
    }
    scanner.close();
  }

}
