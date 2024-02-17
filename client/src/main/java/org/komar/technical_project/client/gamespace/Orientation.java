package org.komar.technical_project.client.gamespace;

public enum Orientation {
  NONE("None", " "),
  VERTICAL("v", "вертикально"),
  HORIZONTAL("h", "горизонтально");

  private String orientation;
  private String name;

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

  public static String getNameByAbbreviation(String orientation) {
    for (Orientation o : Orientation.values()) {
      if (o.orientation.equals(orientation)) {
        return o.getName();
      }
    }
    return null;
  }

}
