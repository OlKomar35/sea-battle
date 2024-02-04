package org.komar.technical_project.gameplay;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import org.komar.technical_project.gamespace.Coordinates;
import org.komar.technical_project.gamespace.Ship;
import org.komar.technical_project.gamers.Player;
import org.komar.technical_project.helper.ConsoleHelper;
import org.komar.technical_project.helper.GameElements;
import org.komar.technical_project.helper.TextColor;

public class Gameplay {

  private final Player player1;
  private Player player2;
  private Scanner scanner;

  private final String ELEMENT_SHIP = GameElements.ONE_ELEMENT.getNameElement();
  private final String MISSED = TextColor.ANSI_YELLOW.getColorText()
      + GameElements.MISSED.getNameElement() + TextColor.ANSI_BLUE.getColorText();
  private final String KILLED = TextColor.ANSI_YELLOW.getColorText()
      + GameElements.KILLED.getNameElement() + TextColor.ANSI_BLUE.getColorText();
  private final String BUSY = GameElements.BUSY.getNameElement();
  private boolean winner;

  public Gameplay(Player player1,
                  Player player2,
                  Scanner scanner) {
    this.player1 = player1;
    this.player2 = player2;
    this.scanner = scanner;
    this.winner = false;

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
      ConsoleHelper.clearConsole();
    } else {
      ConsoleHelper.getMsgInvalidCommandEntered();
    }

    Player step = player1;
    Player opponent = player2;
    int countElements = 0;
    while (!winner) {
    /*  try {*/
        //Thread.sleep(2500);
        ConsoleHelper.getMsgCoordinates(step.getName());
     /* } catch (InterruptedException ex) {
        Thread.currentThread().interrupt();
      }*/

      Coordinates coordinates = step.getCoordinates(scanner);
      GameElements gameElements = opponent.checkingForHits(coordinates.getRow(), coordinates.getColumn());
      step.setStatus(gameElements);

      if (gameElements.equals(GameElements.MISSED)) {
        Player temp = step;
        step = opponent;
        opponent = temp;
      } else if (gameElements.equals(GameElements.HURT)) {

      } else if (gameElements.equals(GameElements.KILLED)) {
        System.out.println(opponent.getCountHurtElements());
        System.out.println(step.getCountHurtElements());
        opponent.getSetOfShips().removeShips(Ship.getViewShipByLength(opponent.getCountHurtElements()));
        opponent.setTotalCountShip(opponent.getTotalCountShip() - 1);
        opponent.setCountHurtElements(0);
        System.out.printf("Осталось у противника %d кораблей\n", opponent.getTotalCountShip());
        if (opponent.getTotalCountShip() == 0) {
          step.setWinner(true);
          winner = true;
          ConsoleHelper.getWinnerMsg(step.getName());
        }
        fillGameFieldOpponent(opponent);
      }
      ConsoleHelper.clearConsole();
      if (!winner) {
        showGeneralGameField();
      }
    }
  }

  private void fillGameFieldOpponent(Player player) {
    int rows = player.getGameField().getGameFieldMatrix().length;
    int cols = player.getGameField().getGameFieldMatrix()[0].length;

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (player.getGameField().getGameFieldMatrix()[i][j] != null &&
            player.getGameField().getGameFieldMatrix()[i][j].equals(KILLED)) {
          checkSurroundingElements(player.getGameField().getGameFieldMatrix(), i, j);
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
          if (matrix[i][j].equals(BUSY)) {
            matrix[i][j] = MISSED;
          } else if (matrix[i][j] == null) {
            matrix[i][j] = MISSED;
          }
        }
      }
    }
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
           /* && !player2.getGameField().getGameFieldMatrix()[row][column].equals(ELEMENT_SHIP)*/) {
          System.out.print(player2.getGameField().getGameFieldMatrix()[row][column] + " ");
        } else {
          System.out.print("~ ");
        }
      }
      System.out.println();
    }

    System.out.println();

    System.out.print("  Ваши корабли, которые остались         ");
    System.out.println("Корабли противника, которые остались");
    Iterator<Map.Entry<Ship, Integer>> iterator1 = player1.getSetOfShips().getCompleteSetOfShips().entrySet()
        .iterator();
    Iterator<Map.Entry<Ship, Integer>> iterator2 = player2.getSetOfShips().getCompleteSetOfShips().entrySet()
        .iterator();
    String emptyPane = "                   ";
    while (iterator1.hasNext() && iterator2.hasNext()) {
      Map.Entry<Ship, Integer> entry1 = iterator1.next();
      Map.Entry<Ship, Integer> entry2 = iterator2.next();
      System.out.print(entry1.getKey().getViewShip() + " - " + entry1.getValue() + " штук");
      emptyPane += "  ";
      System.out.print(emptyPane);
      System.out.println(entry2.getKey().getViewShip() + " - " + entry2.getValue() + " штук");
    }
    System.out.println(TextColor.ANSI_RESET.getColorText());
  }
}
