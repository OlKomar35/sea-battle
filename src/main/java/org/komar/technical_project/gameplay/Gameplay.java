package org.komar.technical_project.gameplay;

import java.util.Arrays;
import java.util.Scanner;
import org.komar.technical_project.bot.Bot;
import org.komar.technical_project.game.Ship;
import org.komar.technical_project.gamer1.Player;
import org.komar.technical_project.helper.ConsoleHelper;
import org.komar.technical_project.helper.GameElements;
import org.komar.technical_project.helper.TextColor;

public class Gameplay {

  private final Player player1;
  private Player player2;

  private final String ELEMENT_SHIP = Ship.ONE_ELEMENT.getViewShip();
  private final String MISSED = GameElements.MISSED.getNameElement();
  private final String KILLED = GameElements.KILLED.getNameElement();
  private final String BUSY = GameElements.BUSY.getNameElement();
  private boolean winner;

  public Gameplay() {

    Scanner scanner = new Scanner(System.in, "UTF-8");

    this.winner = false;
    System.out.println("\n Введите ваше имя: ");
    String nameGamer1 = scanner.nextLine();
    this.player1 = new Player(nameGamer1);
    ConsoleHelper.getMsgWelcome(nameGamer1);

    ConsoleHelper.getMsgChoosingOpponent();

    this.player2 = null;
    boolean isSelectedOpponent = false;

    while (!isSelectedOpponent) {
      String nameGamer2 = scanner.nextLine();
      if (nameGamer2.startsWith("gamer2")) {
        String[] partMsg = nameGamer2.split(" -");
        String msg = partMsg[1];

        if (msg.equals("bot")) {
          this.player2 = new Bot();
        } else if (msg.equals("p")) {
          this.player2 = new Player("");
        }
        isSelectedOpponent = true;
      } else {
        ConsoleHelper.getMsgInvalidCommandEntered();
      }
    }

    System.out.println("-------------------------------------------------------------------------");
    System.out.printf("%s и  %s, можем приступать к игре\n", player1.getName(), player2.getName());
    System.out.println("-------------------------------------------------------------------------");
    System.out.println("Заполните ваши игровые поля кораблями\n");

    ConsoleHelper.getMsgFillGameField();

    String coordinatesShipMsg = scanner.nextLine();

    if (coordinatesShipMsg.startsWith("ship")) {
      int totalCountShip = player1.getSetOfShips().getTotalShipsCount();
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

          if (player1.getGameField()
              .isBusyGameFieldCells(lengthShip, rowCoordinate, columnCoordinateChar, orientation)) {
            player1.getGameField().fillGameField(lengthShip, rowCoordinate, columnCoordinateChar, orientation);
            showGeneralGameField();
            player1.getSetOfShips().removeShips(Ship.getViewShipByLength(lengthShip));
            player1.getSetOfShips().viewCountShips();
            totalCountShip--;
          }
        }
      } else if (msg.equals("r")) {
        player1.getGameField().randomFillGameField(player1.getSetOfShips().getCompleteSetOfShips());
        showGeneralGameField();
      }
    } else {
      ConsoleHelper.getMsgInvalidCommandEntered();
    }

    ConsoleHelper.clearConsole();

    Player step = player1;
    while (!winner) {
      ConsoleHelper.getMsgCoordinates(step.getName());
      String coordinates = scanner.nextLine();
      String[] partMsg = coordinates.split("-");

      int row = Integer.parseInt(partMsg[0]);
      char columnChar = partMsg[1].charAt(0);
      int column = step.getGameField().getColumnsNameList().indexOf(columnChar);

      boolean flag = step.getGameField().isStepPlayer();
      while (flag) {
        step.getGameField().playerWalks(row, column);
        flag = step.getGameField().isStepPlayer();

      }
      if (step.getName().equals(player1.getName())) {
        step = player2;
      } else {
        step = player1;
      }
      showGeneralGameField();
    }
    scanner.close();
  }

  public void showGeneralGameField() {
    System.out.print("    ");
    for (char c : player1.getGameField().getColumnsNameList()) {
      System.out.print(TextColor.ANSI_BLUE.getColorText() + c + " ");
    }
    System.out.print("       ");
    for (char c : player2.getGameField().getColumnsNameList()) {
      System.out.print(TextColor.ANSI_BLUE.getColorText() + c + " ");
    }
    System.out.println();
    for (int row = 0; row < player1.getGameField().getRowsNameList().length; row++) {
      if (row < 9) {
        System.out.print(" " + player1.getGameField().getRowsNameList()[row] + "  ");
      } else {
        System.out.print(" " + player1.getGameField().getRowsNameList()[row] + " ");
      }
      for (int column = 0; column < player1.getGameField().getGameFieldMatrix()[0].length; column++) {
        if (player1.getGameField().getGameFieldMatrix()[row][column] != null
            && !player1.getGameField().getGameFieldMatrix()[row][column].equals(BUSY)) {
          System.out.print(player1.getGameField().getGameFieldMatrix()[row][column] + " ");
        } else {
          System.out.print("~ ");
        }
      }
      System.out.print("   ");
      if (row < 9) {
        System.out.print(" " + player2.getGameField().getRowsNameList()[row] + "  ");
      } else {
        System.out.print(" " + player2.getGameField().getRowsNameList()[row] + " ");
      }
      for (int column = 0; column < player2.getGameField().getGameFieldMatrix()[0].length; column++) {
        if (player2.getGameField().getGameFieldMatrix()[row][column] != null
            && !player2.getGameField().getGameFieldMatrix()[row][column].equals(BUSY)
            && !player2.getGameField().getGameFieldMatrix()[row][column].equals(ELEMENT_SHIP)) {
          System.out.print(player2.getGameField().getGameFieldMatrix()[row][column] + " ");
        } else {
          System.out.print("~ ");
        }
      }
      System.out.println();
    }
    player1.getSetOfShips().viewCountShips();
    player2.getSetOfShips().viewCountShips();
    System.out.println(TextColor.ANSI_RESET.getColorText());
  }
}
