package org.komar.technical_project.client.gamespace;

public enum Orientation {
  NONE("None"),
  VERTICAL("v"),
  HORIZONTAL("h");

  private String orientation;
  Orientation(String orientation) {
    this.orientation = orientation;
  }

  public String getOrientation() {
    return orientation;
  }

}
