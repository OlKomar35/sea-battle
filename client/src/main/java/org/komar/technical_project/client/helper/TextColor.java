package org.komar.technical_project.client.helper;

public enum TextColor {
  ANSI_RESET("\u001B[0m"),
  ANSI_GREEN("\u001B[32m"),
  ANSI_RED("\u001B[31m"),
  ANSI_PURPLE("\u001B[35m"),
  ANSI_WHITE("\u001B[37m"),
  ANSI_YELLOW("\u001B[33m"),
  ANSI_BLUE("\u001B[34m");


  private final String colorText;

  /**
   * Перечисление цветов, для вывода разноцветного текста в консоль
   * @param colorText код цвета
   */
  TextColor(String colorText) {
    this.colorText = colorText;
  }

  public String getColorText() {
    return this.colorText;
  }
}
