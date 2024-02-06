package org.komar.technical_project.gamers;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import org.komar.technical_project.helper.ConsoleHelper;
import org.komar.technical_project.helper.DateTimeHelper;
import org.komar.technical_project.helper.FileHelper;
import org.komar.technical_project.helper.GameElements;
import org.komar.technical_project.helper.TextColor;

public class Admin {

  private Scanner scanner;
  private boolean isExit;
  private List<String> listFiles;
  private final String MISSED = TextColor.ANSI_RED.getColorText()
      + GameElements.MISSED.getNameElement() + TextColor.ANSI_RESET.getColorText();
  private final String KILLED = TextColor.ANSI_RED.getColorText()
      + GameElements.KILLED.getNameElement() + TextColor.ANSI_RESET.getColorText();
  private final String HURT = TextColor.ANSI_RED.getColorText()
      + GameElements.HURT.getNameElement() + TextColor.ANSI_RESET.getColorText();

  private final int[] rowsNameList = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
  private final List<Character> columnsNameList
      = Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p');
  private Object[][] gameFieldMatrixPlayer1;
  private Object[][] gameFieldMatrixPlayer2;
  private int numPlayer;

  public Admin(Scanner scanner) {
    this.scanner = scanner;
    this.isExit = false;
    this.gameFieldMatrixPlayer1 = new Object[rowsNameList.length][columnsNameList.size()];
    this.gameFieldMatrixPlayer2 = new Object[rowsNameList.length][columnsNameList.size()];
    this.numPlayer = 1;

    while (!isExit) {
      this.listFiles = new ArrayList<>();
      ConsoleHelper.clearConsole();
      File folder = new File(FileHelper.getRootDirPath());
      File[] files = folder.listFiles();
      System.out.println("Архив игр: ");
      if (files != null) {
        for (File file : files) {
          if (file.isFile()) {
            String dateString = file.getName().split("_")[1].replace(".txt", "");
            listFiles.add(dateString);
            LocalDateTime dateTime = LocalDateTime.parse(dateString, DateTimeHelper.getDateTimeFormatFused());
            String fileName = file.getName().split("_")[0]
                .concat(" ").concat(dateTime.format(DateTimeHelper.getDateTimeFormat()));
            System.out.println(listFiles.size() + " " + fileName);
          }
        }
      }

      System.out.println("\nЧтобы просмотреть одну из сохраненных игр, введите команду "
                             + TextColor.ANSI_PURPLE.getColorText() + " --file -[номер файла]"
                             + TextColor.ANSI_RESET.getColorText());
      System.out.println("\nЧтобы заархивировать одну из сохраненных игр, введите команду "
                             + TextColor.ANSI_PURPLE.getColorText() + " --archive -[номер файла]"
                             + TextColor.ANSI_RESET.getColorText());
      System.out.println("\nЧтобы удалить одну из сохраненных игр, введите команду "
                             + TextColor.ANSI_PURPLE.getColorText() + " --delete -[номер файла]"
                             + TextColor.ANSI_RESET.getColorText());
      System.out.println("\nЕсли хотите выйти, введите команду"
                             + TextColor.ANSI_PURPLE.getColorText() + " --exit"
                             + TextColor.ANSI_RESET.getColorText());
      String massage = scanner.nextLine();

      if (massage.equals("--exit")) {
        isExit = true;

      } else if (massage.startsWith("--file -")) {
        int num = Integer.parseInt(massage.split("-")[3]);
        String nameFile = "game_" + listFiles.get(num - 1) + ".txt";

        try {
          BufferedReader reader = new BufferedReader(
              new FileReader(FileHelper.getRootDirPath() + FileHelper.fileSeparator + nameFile));

          String lineStr;
          String namePlayer1 = "";
          String namePlayer2 = "";

          while ((lineStr = reader.readLine()) != null) {
            System.out.printf("%s\n", lineStr);

            if (lineStr.startsWith("Игрок 1")) {
              namePlayer1 = lineStr.split(":")[1].trim().split(" ")[0];
              namePlayer2 = lineStr.split(":")[2].trim();
            }

            if (lineStr.startsWith("_") && numPlayer == 1) {
              String[] listElements = lineStr.split(" ");
              int row = Integer.parseInt(listElements[0].split("_")[1].trim());
              if (row < 10) {
                for (int col = 2; col < listElements.length; col++) {
                  gameFieldMatrixPlayer1[row - 1][col - 2] = listElements[col].trim();
                }
              } else {
                for (int col = 1; col < listElements.length; col++) {
                  gameFieldMatrixPlayer1[row - 1][col - 1] = listElements[col].trim();
                }
              }
            }

            if (lineStr.trim().equalsIgnoreCase("Игровое поле второго игрока")) {
              this.numPlayer = 2;
            }

            if (lineStr.startsWith("_") && numPlayer == 2) {
              String[] listElements = lineStr.split(" ");
              int row = Integer.parseInt(listElements[0].split("_")[1].trim());
              if (row < 10) {
                for (int col = 2; col < listElements.length; col++) {
                  gameFieldMatrixPlayer2[row - 1][col - 2] = listElements[col].trim();
                }
              } else {
                for (int col = 1; col < listElements.length; col++) {
                  gameFieldMatrixPlayer2[row - 1][col - 1] = listElements[col].trim();
                }
              }
            }

            if (lineStr.startsWith("Игрок " + namePlayer1)) {
              int row = Integer.parseInt(lineStr.split("\\[")[1].split("]")[0].split("-")[0]);
              String col = lineStr.split("\\[")[1].split("]")[0].split("-")[1].trim();
              char colChar = col.charAt(0);
              String status = getStatus(lineStr.split("]")[1].split("-")[1]);

              gameFieldMatrixPlayer2[row - 1][columnsNameList.indexOf(colChar)] = status;
              System.out.println("Игровое поле " + TextColor.ANSI_RED.getColorText() + namePlayer2
                                     + TextColor.ANSI_RESET.getColorText());
              showGameField(gameFieldMatrixPlayer2);
            }

            if (lineStr.startsWith("Игрок " + namePlayer2)) {
              int row = Integer.parseInt(lineStr.split("\\[")[1].split("]")[0].split("-")[0]);
              String col = lineStr.split("\\[")[1].split("]")[0].split("-")[1].trim();
              char colChar = col.charAt(0);
              String status = getStatus(lineStr.split("]")[1].split("-")[1]);

              gameFieldMatrixPlayer1[row - 1][columnsNameList.indexOf(colChar)] = status;
              System.out.println("Игровое поле " + TextColor.ANSI_RED.getColorText() + namePlayer1
                                     + TextColor.ANSI_RESET.getColorText());
              showGameField(gameFieldMatrixPlayer1);
            }
          }
          System.out.println();

          reader.close();
        } catch (IOException e) {
          throw new RuntimeException(e);
        }

      } else if (massage.startsWith("--archive -")) {
        int num = Integer.parseInt(massage.split("-")[3]);
        String nameFile = "game_" + listFiles.get(num - 1) + ".txt";

        Path path = Paths.get(FileHelper.getArchiveDirPath());

        if (!Files.exists(path)) {
          try {
            Files.createDirectories(path);
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
        try {
          Files.move(Paths.get(FileHelper.getRootDirPath() + FileHelper.fileSeparator + nameFile),
                     Paths.get(FileHelper.getArchiveDirPath() + FileHelper.fileSeparator + nameFile));

          System.out.println(TextColor.ANSI_PURPLE.getColorText() + "Файл " + nameFile + " успешно заархивирован"
                                 + TextColor.ANSI_RESET.getColorText());
        } catch (Exception e) {
          e.printStackTrace();
        }
      } else if (massage.startsWith("--delete -")) {
        int num = Integer.parseInt(massage.split("-")[3]);
        String nameFile = "game_" + listFiles.get(num - 1) + ".txt";

        try {
          Files.delete(Paths.get(FileHelper.getRootDirPath() + FileHelper.fileSeparator + nameFile));
        } catch (IOException e) {
          throw new RuntimeException(e);
        }

      }
    }
    System.exit(0);
  }

  private void showGameField(Object[][] gameFieldMatrixPlayer) {
    System.out.println();
    System.out.print("    ");
    for (char c : columnsNameList) {
      System.out.print(c + " ");
    }
    System.out.println();
    for (int row = 0; row < rowsNameList.length; row++) {
      if (row < 9) {
        System.out.print(" " + rowsNameList[row] + "  ");
      } else {
        System.out.print(" " + rowsNameList[row] + " ");
      }
      for (int column = 0; column < gameFieldMatrixPlayer[0].length; column++) {
        if (gameFieldMatrixPlayer[row][column] != null) {
          System.out.print(gameFieldMatrixPlayer[row][column] + " ");
        }
      }
      System.out.println();
    }
    System.out.println("Нажмите Enter");
    scanner.nextLine();
    ConsoleHelper.clearConsole();
  }

  private String getStatus(String s) {
    if (s.trim().equalsIgnoreCase("Мимо")) {
      return MISSED;
    } else if (s.trim().equalsIgnoreCase("Ранил")) {
      return HURT;
    } else if (s.trim().equalsIgnoreCase("Убит")) {
      return KILLED;
    }
    return null;
  }
}
