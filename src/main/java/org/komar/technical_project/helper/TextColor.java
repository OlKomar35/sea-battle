package org.komar.technical_project.helper;

public enum TextColor {
  ANSI_RESET("\u001B[0m"),
  ANSI_GREEN("\u001B[32m"),
  ANSI_RED("\u001B[31m"),
  ANSI_BLUE("\u001B[34m");


  private final String colorText;

  TextColor(String colorText) {
    this.colorText = colorText;
  }

  public String getColorText() {
    return this.colorText;
  }
}
