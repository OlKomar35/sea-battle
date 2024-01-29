package org.komar.technical_project.gamespace;

import java.util.HashMap;
import java.util.Map;
import org.komar.technical_project.helper.TextColor;

public class SetOfShips {
  protected Map<Ship, Integer> completeSetOfShips;
  protected int totalCount = 0;

  public SetOfShips() {
    completeSetOfShips = new HashMap<>();
    completeSetOfShips.put(Ship.ONE, Ship.ONE.getMaxCountShip());
    completeSetOfShips.put(Ship.TWO, Ship.TWO.getMaxCountShip());
    completeSetOfShips.put(Ship.THREE, Ship.THREE.getMaxCountShip());
    completeSetOfShips.put(Ship.FOUR, Ship.FOUR.getMaxCountShip());
    completeSetOfShips.put(Ship.FIVE, Ship.FIVE.getMaxCountShip());
    completeSetOfShips.put(Ship.SIX, Ship.SIX.getMaxCountShip());

    for (Map.Entry<Ship, Integer> entry : completeSetOfShips.entrySet()) {
      totalCount += entry.getValue();
    }

  }

  public void removeShips(Ship ship) {
    if (this.completeSetOfShips.get(ship) > 0) {
      int oldValue = completeSetOfShips.get(ship);
      completeSetOfShips.put(ship, oldValue - 1);
    }
  }

  public int getTotalShipsCount() {
    return totalCount;
  }

  public void viewCountShips() {
    System.out.println(Ship.SIX.getViewShip() + " - " + completeSetOfShips.get(Ship.SIX) + " штук");
    System.out.println(Ship.FIVE.getViewShip() + " - " + completeSetOfShips.get(Ship.FIVE) + " штук");
    System.out.println(Ship.FOUR.getViewShip() + " - " + completeSetOfShips.get(Ship.FOUR) + " штук");
    System.out.println(Ship.THREE.getViewShip() + " - " + completeSetOfShips.get(Ship.THREE) + " штук");
    System.out.println(Ship.TWO.getViewShip() + " - " + completeSetOfShips.get(Ship.TWO) + " штук");
    System.out.println(
        Ship.ONE.getViewShip() + " - " + completeSetOfShips.get(Ship.ONE) + " штук" + TextColor.ANSI_RESET.getColorText());
  }

  public Map<Ship, Integer> getCompleteSetOfShips() {
    return completeSetOfShips;
  }
}
