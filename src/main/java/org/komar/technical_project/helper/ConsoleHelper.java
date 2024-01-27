package org.komar.technical_project.helper;

public class ConsoleHelper {

  public static void clearConsole() {
    System.out.print("\033[H\033[2J");
//    System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
//                           + "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
//    try {
//      String operatingSystem = System.getProperty("os.name"); // Получаем операционную систему
//      if (operatingSystem.contains("Windows")) {
//        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor(); // Для Windows используем "cls"
//      } else {
//        new ProcessBuilder("bash", "-c", "clear").inheritIO().start().waitFor(); // Для других ОС используем "clear"
//      }
//    } catch (Exception e) {
//      System.out.println("Произошла ошибка при очистке терминала: " + e.getMessage());
//    }
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
        "например: " + TextColor.ANSI_GREEN.getColorText() + "\nship - p - 5 - 3 - g - v"
            + TextColor.ANSI_RESET.getColorText());
    System.out.println("Для автогенерации игрового поля нужно ввести команду:\n" + TextColor.ANSI_GREEN.getColorText()
                           + "ship -r" + TextColor.ANSI_RESET.getColorText());
  }

  public static void getMsgWelcome(String nameGamer) {
    System.out.printf("Добро пожаловать, %s\n", nameGamer);
  }

  public static void getMsgChoosingOpponent() {
    System.out.println("Выберите соперника");
    System.out.println("для игры с искусственным интеллектом введите команду:\n" + TextColor.ANSI_GREEN.getColorText()
                           + "gamer2 -bot" + TextColor.ANSI_RESET.getColorText());
    System.out.println("для игры с напарником:\n" + TextColor.ANSI_GREEN.getColorText()
                           + "gamer2 -p" + TextColor.ANSI_RESET.getColorText());
  }

  public static void getMsgMissed(){
    System.out.println( TextColor.ANSI_PURPLE.getColorText()+"МИМО"+ TextColor.ANSI_RESET.getColorText());
    System.out.println("Ход переходит к противнику");
  }

  public static void getMsgKilled(){
    System.out.println( TextColor.ANSI_RED.getColorText()+"УБИТ"+ TextColor.ANSI_RESET.getColorText());
    System.out.println("Ходите снова");
  }

  public static void getMsgHurt(){
    System.out.println( TextColor.ANSI_RED.getColorText()+"РАНИЛ"+ TextColor.ANSI_RESET.getColorText());
    System.out.println("Ходите снова");
  }

  public static void getMsgLoser(){
    System.out.println( TextColor.ANSI_PURPLE.getColorText()+"Жаль, но в данную точку вы уже стреляли "
                            + TextColor.ANSI_RESET.getColorText());
    System.out.println("Ходите снова");
  }

  public static void getMsgCoordinates(String name){
    System.out.printf("%s введите координаты (строка-столбец)", name);
  }
}
