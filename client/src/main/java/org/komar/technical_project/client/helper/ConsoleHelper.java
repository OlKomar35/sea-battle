package org.komar.technical_project.client.helper;

public class ConsoleHelper {

  /**
   * Класс помощник с сообщениями для вывода в консоль
   */

  public static void clearConsole() {
    System.out.println("\033[H\033[2J");
  }

  public static void getMsgInvalidCommandEntered() {
    System.out.println("Введена неверная команда. Попробуйте снова");
    // System.out.println("Если у вас возникают трудности напишите команду help");
  }

  public static void getMsgFillGameField() {
    System.out.println("Для того чтобы заполнить поле кораблями, необходимо использовать следующую команду");
    System.out.println(TextColor.ANSI_GREEN.getColorText()
                           + "ship -p -[размер корабля] -[координата начала расположения (строка)]"
                           + " -[координата начала расположения (столбец)] "
                           + "-[v(вертикальное расположение)/h(горизонтальное расположение)]");
    System.out.println(
        "например: " + TextColor.ANSI_GREEN.getColorText() + "\nship -p -5 -3 -g -v"
            + TextColor.ANSI_RESET.getColorText());
    System.out.println("Для автогенерации игрового поля нужно ввести команду:\n" + TextColor.ANSI_GREEN.getColorText()
                           + "ship -r" + TextColor.ANSI_RESET.getColorText());
  }

  public static void getMsgWelcome(String nameGamer) {
    System.out.printf("Добро пожаловать, %s\n", nameGamer);
  }

  public static void getMsgChoosingOpponent() {
    System.out.println("\nВыберите соперника");
    System.out.println("для игры с искусственным интеллектом введите команду:\n" + TextColor.ANSI_GREEN.getColorText()
                           + "gamer2 -bot" + TextColor.ANSI_RESET.getColorText());
    System.out.println("для игры с напарником:\n" + TextColor.ANSI_GREEN.getColorText()
                           + "gamer2 -p" + TextColor.ANSI_RESET.getColorText());
    System.out.println("для игры с напарником по сети:\n" + TextColor.ANSI_GREEN.getColorText()
                           + "gamer2 -np" + TextColor.ANSI_RESET.getColorText());
  }

  public static void getMsgMissed() {
    System.out.println(TextColor.ANSI_PURPLE.getColorText() + "\nМИМО" + TextColor.ANSI_RESET.getColorText());
    System.out.println("Ход переходит к противнику");
  }

  public static void getMsgKilled() {
    System.out.println(TextColor.ANSI_RED.getColorText() + "\nУБИТ" + TextColor.ANSI_RESET.getColorText());
    System.out.println("Ходите снова");
  }

  public static void getMsgHurt() {
    System.out.println(TextColor.ANSI_RED.getColorText() + "\nРАНИЛ" + TextColor.ANSI_RESET.getColorText());
    System.out.println("Ходите снова");
  }

  public static void getMsgLoser() {
    System.out.println(TextColor.ANSI_PURPLE.getColorText() + "\nЖаль, но в данную точку вы уже стреляли "
                           + TextColor.ANSI_RESET.getColorText());
    System.out.println("Ход переходит к противнику");
  }

  public static void getMsgCoordinates(String name) {
    System.out.printf("%s, ваш ход.\n", name);
    System.out.print("введите координаты " + TextColor.ANSI_GREEN.getColorText() + "строка-столбец "
                         + TextColor.ANSI_RESET.getColorText());
  }

  public static void getWinnerMsg(String name) {
    System.out.println();
    System.out.printf(TextColor.ANSI_RED.getColorText() + "П"
                          + TextColor.ANSI_YELLOW.getColorText() + "о"
                          + TextColor.ANSI_GREEN.getColorText() + "з"
                          + TextColor.ANSI_PURPLE.getColorText() + "д"
                          + TextColor.ANSI_BLUE.getColorText() + "р"
                          + TextColor.ANSI_RED.getColorText() + "а"
                          + TextColor.ANSI_YELLOW.getColorText() + "в"
                          + TextColor.ANSI_GREEN.getColorText() + "л"
                          + TextColor.ANSI_PURPLE.getColorText() + "я"
                          + TextColor.ANSI_BLUE.getColorText() + "е"
                          + TextColor.ANSI_RED.getColorText() + "м"
                          + TextColor.ANSI_YELLOW.getColorText() + " %s,"
                          + TextColor.ANSI_GREEN.getColorText() + "В"
                          + TextColor.ANSI_PURPLE.getColorText() + "ы"
                          + TextColor.ANSI_BLUE.getColorText() + " п"
                          + TextColor.ANSI_RED.getColorText() + "о"
                          + TextColor.ANSI_YELLOW.getColorText() + "б"
                          + TextColor.ANSI_GREEN.getColorText() + "е"
                          + TextColor.ANSI_PURPLE.getColorText() + "д"
                          + TextColor.ANSI_BLUE.getColorText() + "и"
                          + TextColor.ANSI_RED.getColorText() + "л"
                          + TextColor.ANSI_YELLOW.getColorText() + "и"
                          + TextColor.ANSI_GREEN.getColorText() + "!"
                          + TextColor.ANSI_PURPLE.getColorText() + "!"
                          + TextColor.ANSI_BLUE.getColorText() + "!"
                          + TextColor.ANSI_RED.getColorText() + "!"

                          + TextColor.ANSI_RESET.getColorText(), name);
  }
}
