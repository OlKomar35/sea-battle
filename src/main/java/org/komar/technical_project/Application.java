package org.komar.technical_project;

import java.util.Scanner;
import org.komar.technical_project.bot.Bot;
import org.komar.technical_project.gameplay.Gameplay;
import org.komar.technical_project.gamer1.Player;
import org.komar.technical_project.helper.ConsoleHelper;
import org.komar.technical_project.helper.TextColor;

public class Application {

  public static void main(String[] args) {

    ConsoleHelper.clearConsole();

    System.out.println(TextColor.ANSI_GREEN.getColorText() + "* * * * * * * * * * * * * * * * *  * * *");
    System.out.println("*              Морской бой             *");
    System.out.println("* * * * * * * * * * * * * * * * * * *  *" + TextColor.ANSI_RESET.getColorText());


    new Gameplay();

  }
}
