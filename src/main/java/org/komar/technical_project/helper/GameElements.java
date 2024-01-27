package org.komar.technical_project.helper;

public enum GameElements {
  MISSED("\u002E"),
  KILLED("X"),
  BUSY("+");

  private final String nameElement;
  GameElements(String nameElement) {
    this.nameElement = nameElement;
  }

  public String getNameElement() {
    return nameElement;
  }
}
