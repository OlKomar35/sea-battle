package org.komar.technical_project.client;

import java.io.IOException;
import java.util.Scanner;
import org.komar.technical_project.client.gameplay.Gameplay;
import org.komar.technical_project.client.gamers.Admin;
import org.komar.technical_project.client.gamers.Bot;
import org.komar.technical_project.client.gamers.Human;
import org.komar.technical_project.client.network.Network;
import org.komar.technical_project.client.gamers.NetworkPlayer;
import org.komar.technical_project.client.gamers.Player;
import org.komar.technical_project.client.helper.ConsoleHelper;
import org.komar.technical_project.client.helper.TextColor;

/**
 * Главный класс в котором находится точка входа в игру
 *
 * @author Комар Ольга
 * @version 1.0
 */

public class Application {

  public static void main(String[] args) {

    ConsoleHelper.clearConsole();

    System.out.println(TextColor.ANSI_GREEN.getColorText() + "* * * * * * * * * * * * * * * * *  * * *");
    System.out.println("*              Морской бой             *");
    System.out.println("* * * * * * * * * * * * * * * * * * *  *" + TextColor.ANSI_RESET.getColorText());

    Scanner scanner = new Scanner(System.in, "UTF-8");

    while (true) {
      System.out.println("Для авторизации с правами администратора введите команду"
                             + TextColor.ANSI_GREEN.getColorText() + " --admin" + TextColor.ANSI_RESET.getColorText()
                             + ", если хотите начать игру нажмите "
                             + TextColor.ANSI_GREEN.getColorText() + "Enter" + TextColor.ANSI_RESET.getColorText());
      System.out.println("Для выхода введите команду "
                             + TextColor.ANSI_GREEN.getColorText() + "--exit" + TextColor.ANSI_RESET.getColorText());
      String command = scanner.nextLine();

      if (command.equals("--exit")) {
        System.exit(0);
      } else if (command.equals("--admin")) {
        new Admin(scanner);
      } else {
        Player player1 = getPlayer(scanner);
        ConsoleHelper.getMsgChoosingOpponent();

        Player player2 = null;
        boolean isSelectedOpponent = false;

        while (!isSelectedOpponent) {
          String nameGamer2 = scanner.nextLine();
          if (nameGamer2.startsWith("gamer2 ")) {

            try {
              String[] partMsg = nameGamer2.split(" -");
              String msg = partMsg[1];

              if (msg.equals("bot")) {
                isSelectedOpponent = true;
                player2 = new Bot();
              } else if (msg.equals("p")) {
                isSelectedOpponent = true;
                player2 = getPlayer(scanner);
              } else if (msg.equals("np")) {
                isSelectedOpponent = true;
                try {
                  Network network = new Network(scanner);
                  player2 = new NetworkPlayer();
                } catch (IOException e) {
                  throw new RuntimeException(e);
                }
              } else {
                ConsoleHelper.getMsgInvalidCommandEntered();
                isSelectedOpponent = false;
              }
            } catch (ArrayIndexOutOfBoundsException e) {
              ConsoleHelper.getMsgInvalidCommandEntered();
              isSelectedOpponent = false;
            }
          } else {
            ConsoleHelper.getMsgInvalidCommandEntered();
          }
        }
        new Gameplay(player1, player2, scanner);
      }
    }
  }

  public static Player getPlayer(Scanner scanner) {
    System.out.println("\nВведите ваше имя: ");
    String nameGamer1 = scanner.nextLine();
    Player player1 = new Human(nameGamer1, scanner);
    ConsoleHelper.getMsgWelcome(nameGamer1);
    return player1;
  }
}
