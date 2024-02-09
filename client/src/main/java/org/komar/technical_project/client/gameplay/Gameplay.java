package org.komar.technical_project.client.gameplay;

import static org.komar.technical_project.client.helper.FileHelper.fileSeparator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import org.komar.technical_project.client.gamers.Bot;
import org.komar.technical_project.client.gamers.Player;
import org.komar.technical_project.client.gamespace.Coordinates;
import org.komar.technical_project.client.gamespace.Ship;
import org.komar.technical_project.client.helper.ConsoleHelper;
import org.komar.technical_project.client.helper.DateTimeHelper;
import org.komar.technical_project.client.helper.FileHelper;
import org.komar.technical_project.client.helper.GameElements;
import org.komar.technical_project.client.helper.TextColor;

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

    LocalDateTime dateTime = LocalDateTime.now();
    String formattedDateForFile = dateTime.format(DateTimeHelper.getDateTimeFormatFused());
    String gameFileName = "game_" + formattedDateForFile + ".txt";

    Path path = Paths.get(FileHelper.getRootDirPath());

    if (!Files.exists(path)) {
      try {
        Files.createDirectories(path);
      } catch (IOException e) {
        e.printStackTrace();
        return;
      }
    }

    String filePath = FileHelper.getRootDirPath().concat(fileSeparator).concat(gameFileName);
    File file = new File(filePath);

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
      writer.write("Игра была запущена " + LocalDateTime.now().format(DateTimeHelper.getDateTimeFormat()));
      writer.newLine();

      System.out.println(TextColor.ANSI_YELLOW.getColorText()
                             + "Файл для администратора успешно создан (" + gameFileName + ")"
                             + TextColor.ANSI_RESET.getColorText());

      System.out.println("-------------------------------------------------------------------------");
      System.out.printf("%s и  %s, можем приступать к игре\n", player1.getName(), player2.getName());
      System.out.println("-------------------------------------------------------------------------");

      writer.write("Игрок 1: " + player1.getName() + " и ");
      writer.write("Игрок 2: " + player2.getName());
      writer.newLine();

      showGeneralGameField(player1, player2);

      writer.write("Игровое поле первого игрока ");
      player1.getGameField().writeToFileGameBoard(writer);
      writer.newLine();
      writer.write("Игровое поле второго игрока ");
      player2.getGameField().writeToFileGameBoard(writer);
      writer.newLine();

      Player step = player1;
      Player opponent = player2;
      int countElements = 0;
      while (!winner) {
        step.setCountSteps(step.getCountSteps() + 1);
        ConsoleHelper.getMsgCoordinates(step.getName());

        Coordinates coordinates = step.getCoordinates(scanner);
        if (step instanceof Bot) {
          try {
            Thread.sleep(1500);
          } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
          }
        }
        ConsoleHelper.clearConsole();

        GameElements gameElements = opponent.checkingForHits(coordinates.getRow(), coordinates.getColumn());
        step.setStatus(gameElements);

        writer.write("Игрок " + step.getName() + " сделал ход ["
                         + step.getGameField().getRowsNameList()[coordinates.getRow() - 1] + "-"
                         + step.getGameField().getColumnsNameList().get(coordinates.getColumn())
                         + "] в " + LocalTime.now().format(DateTimeHelper.getTimeFormat()));
        writer.write("-" + step.getStatus().getStatus());
        writer.newLine();

        if (gameElements.equals(GameElements.MISSED)) {
          Player temp = step;
          step = opponent;
          opponent = temp;
        } else if (gameElements.equals(GameElements.HURT)) {
          step.setCountFineSteps(step.getCountSteps() + 1);
        } else if (gameElements.equals(GameElements.KILLED)) {
          writer.write("Был убит корабль состоящий из " + opponent.getCountHurtElements() + " элемента(-ов)");
          writer.newLine();

          step.setCountFineSteps(step.getCountSteps() + 1);

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
        if (!winner) {
          if (player2 instanceof Bot) {
            showGeneralGameField(player1, player2);
          } else {
            showGeneralGameField(step, opponent);
          }
        }
      }
      writer.write("Игра окончилась в " + LocalDateTime.now().format(DateTimeHelper.getDateTimeFormat()));
      writer.newLine();

      writer.write("Выиграл игрок: " + step.getName()
                       + ", осталось неубитыми "
                       + (step.getSetOfShips().getTotalShipsCount() - step.getTotalCountShip())
                       + " собственных кораблей");
      writer.newLine();
      writer.write("Было сделано " + step.getCountSteps() + " ходов");
      writer.newLine();
      writer.write("Результативность = [(кол-во попаданий)/(кол-во ходов)*100] = "
                       + (step.getCountFineSteps() * 100) / step.getCountSteps());
      writer.newLine();
      writer.write("Игровое поле первого игрока, на конец игры ");
      //player1.getGameField().getResetColorMatrix();
      player1.getGameField().writeToFileGameBoard(writer);
      writer.newLine();
      writer.write("----------------------------------------------------------------------------------------");
      writer.newLine();
      writer.write("Проиграл игрок: " + opponent.getName()
                       + ", осталось неубитыми "
                       + +(opponent.getSetOfShips().getTotalShipsCount() - opponent.getTotalCountShip())
                       + " собственных кораблей");
      writer.newLine();
      writer.write("Было сделано " + opponent.getCountSteps() + " ходов");
      writer.newLine();
      writer.write("Результативность = [(кол-во попаданий)/(кол-во ходов)*100] = "
                       + (opponent.getCountFineSteps() * 100) / opponent.getCountSteps());
      writer.newLine();
      writer.write("Игровое поле второго игрок, на конец игры ");
      //player2.getGameField().getResetColorMatrix();
      player2.getGameField().writeToFileGameBoard(writer);
      writer.newLine();
      writer.newLine();

      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
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


  public void showGeneralGameField(Player player1,
                                   Player player2) {
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

    System.out.println();

    System.out.print("  Ваши корабли, " + player1.getName() + " ");
    System.out.println("Корабли противника," + player2.getName() + " ,которые остались");
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
