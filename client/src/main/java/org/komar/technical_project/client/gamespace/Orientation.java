package org.komar.technical_project.client.gamespace;

/**
 * Класс-перечисление для ориентации для расположения кораблей
 */
public enum Orientation {
  NONE("None", " "),
  VERTICAL("v", "вертикально"),
  HORIZONTAL("h", "горизонтально");

  private String orientation;
  private String name;

  /**
   * Класс-перечисление для ориентации для расположения кораблей
   * @param orientation ориентация (горизонтально, вертикально)
   * @param name название ориентации для вывода в сообщениях для пользователя
   */
  Orientation(String orientation,
              String name) {
    this.orientation = orientation;
    this.name = name;
  }

  public String getOrientation() {
    return orientation;
  }

  public String getName() {
    return name;
  }

  /**
   * Метод для получения названия по его ориентации
   * @param orientation ориентация
   * @return сообщение для вывода в консоль
   */
  public static String getNameByAbbreviation(String orientation) {
    for (Orientation o : Orientation.values()) {
      if (o.orientation.equals(orientation)) {
        return o.getName();
      }
    }
    return null;
  }

}
