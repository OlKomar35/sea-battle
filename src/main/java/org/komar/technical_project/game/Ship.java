package org.komar.technical_project.game;

public enum Ship {
  SIX("@ @ @ @ @ @", 6),
  FIVE("@ @ @ @ @",5),
  FOUR("@ @ @ @",4),
  THREE("@ @ @", 3),
  TWO("@ @",2),
  ONE("@",1),
  ONE_ELEMENT("@",0);

  private final String viewShip;
  private final int lengthShip;

  Ship(String viewShip, int lengthShip) {
    this.viewShip = viewShip;
    this.lengthShip = lengthShip;
  }

  public String getViewShip() {
    return this.viewShip;
  }

  public static Ship getViewShipByLength(int lengthShip){
    for (Ship ship : Ship.values()) {
      if (ship.getLengthShip() == lengthShip) {
        return ship;
      }
    }
    return null;
  }

  public int getLengthShip() {
    return lengthShip;
  }

  public int getMaxCountShip() {
    return 7 - lengthShip;
  }
}
