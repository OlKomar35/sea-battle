package org.komar.technical_project.helper;

public enum GameElements {
  MISSED("."),
  KILLED("X"),
  HURT("X"),
  BUSY("+"),
  WATER("~"),
  ONE_ELEMENT("@");

  private final String nameElement;
  GameElements(String nameElement) {
    this.nameElement = nameElement;
  }

  public String getNameElement() {
    return nameElement;
  }
}
