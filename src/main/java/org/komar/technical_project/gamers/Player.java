package org.komar.technical_project.gamers;


import org.komar.technical_project.gamespace.GameField;
import org.komar.technical_project.gamespace.SetOfShips;

public class Player {
  protected String name;
  protected GameField gameField;
  protected SetOfShips setOfShips;
  protected int totalCountShip;
  protected boolean winner;

  public Player(String name) {
    this.name = name;
    this.gameField = new GameField();
    this.setOfShips = new SetOfShips();
    this.winner = false;
    this.totalCountShip = setOfShips.getTotalShipsCount();
  }

  public String getName() {
    return name;
  }

  public GameField getGameField() {
    return gameField;
  }

  public SetOfShips getSetOfShips() {
    return setOfShips;
  }

  public void setTotalCountShip(int totalCountShip) {
    this.totalCountShip = totalCountShip;
  }

  public int getTotalCountShip() {
    return totalCountShip;
  }

  public boolean isWinner() {
    return winner;
  }

  public void setWinner(boolean winner) {
    this.winner = winner;
  }
}
