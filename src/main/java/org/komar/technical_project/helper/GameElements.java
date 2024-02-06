package org.komar.technical_project.helper;

public enum GameElements {
  MISSED(".", "Мимо"),
  KILLED("X", "Убит"),
  HURT("X", "Ранил"),
  BUSY("+","Пусто"),
  WATER("~","Вода"),
  ONE_ELEMENT("@", "Элемент корабля");

  private final String nameElement;
  private final String status;
  GameElements(String nameElement, String status) {
    this.nameElement = nameElement;
    this.status = status;
  }

  public String getNameElement() {
    return nameElement;
  }

  public String getStatus() {
    return status;
  }
}
