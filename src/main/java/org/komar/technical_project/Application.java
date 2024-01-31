package org.komar.technical_project;

import java.util.Scanner;
import org.komar.technical_project.gameplay.Gameplay;
import org.komar.technical_project.gamers.Bot;
import org.komar.technical_project.gamers.Human;
import org.komar.technical_project.gamers.Player;
import org.komar.technical_project.helper.ConsoleHelper;
import org.komar.technical_project.helper.TextColor;

public class Application {

  public static void main(String[] args) {

    ConsoleHelper.clearConsole();

    System.out.println(TextColor.ANSI_GREEN.getColorText() + "* * * * * * * * * * * * * * * * *  * * *");
    System.out.println("*              Морской бой             *");
    System.out.println("* * * * * * * * * * * * * * * * * * *  *" + TextColor.ANSI_RESET.getColorText());


    Scanner scanner = new Scanner(System.in, "UTF-8");

    Player player1 = getPlayer(scanner);

    ConsoleHelper.getMsgChoosingOpponent();

    Player player2 = null;
    boolean isSelectedOpponent = false;

    while (!isSelectedOpponent) {
      String nameGamer2 = scanner.nextLine();
      if (nameGamer2.startsWith("gamer2")) {

        String[] partMsg = nameGamer2.split(" -");
        String msg = partMsg[1];

        if (msg.equals("bot")) {
          player2 = new Bot();
        } else if (msg.equals("p")) {
          player2 = getPlayer(scanner);
        }
        isSelectedOpponent = true;
      } else {
        ConsoleHelper.getMsgInvalidCommandEntered();
      }
    }

    new Gameplay(player1, player2 , scanner);

    scanner.close();
  }

  public static Player getPlayer(Scanner scanner){
    System.out.println("\n Введите ваше имя: ");
    String nameGamer1 = scanner.nextLine();
    Player player1 = new Human(nameGamer1);
    ConsoleHelper.getMsgWelcome(nameGamer1);
    return player1;
  }
}
