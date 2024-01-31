package org.komar.technical_project.gamespace;

import java.util.LinkedHashMap;
import java.util.Map;
import org.komar.technical_project.helper.TextColor;

public class SetOfShips {
  protected Map<Ship, Integer> completeSetOfShips;
  protected int totalCount = 0;

  public SetOfShips() {
    completeSetOfShips = new LinkedHashMap<>();
    completeSetOfShips.put(Ship.SIX, Ship.SIX.getMaxCountShip());
    completeSetOfShips.put(Ship.FIVE, Ship.FIVE.getMaxCountShip());
    completeSetOfShips.put(Ship.FOUR, Ship.FOUR.getMaxCountShip());
    completeSetOfShips.put(Ship.THREE, Ship.THREE.getMaxCountShip());
    completeSetOfShips.put(Ship.TWO, Ship.TWO.getMaxCountShip());
    completeSetOfShips.put(Ship.ONE, Ship.ONE.getMaxCountShip());

    for (Map.Entry<Ship, Integer> entry : completeSetOfShips.entrySet()) {
      totalCount += entry.getValue();
    }

  }

  public Map<Ship, Integer> getCompleteSetOfShips() {
    return completeSetOfShips;
  }

  public int getTotalShipsCount() {
    return totalCount;
  }

  public void removeShips(Ship ship) {
    if (this.completeSetOfShips.get(ship) > 0) {
      int oldValue = completeSetOfShips.get(ship);
      completeSetOfShips.put(ship, oldValue - 1);
    }
  }

  public void viewCountShips() {
    for (Map.Entry<Ship, Integer> entry : completeSetOfShips.entrySet()) {
      System.out.println(entry.getKey().getViewShip() + " - " +entry.getValue() + " штук");
    }
  }
}
