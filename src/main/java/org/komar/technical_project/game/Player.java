package org.komar.technical_project.game;

public class Player {
  protected String name;
  protected GameField gameField;
  protected SetOfShips setOfShips;
  protected boolean winner;

  public Player(String name) {
    this.name = name;
    this.gameField = new GameField();
    this.setOfShips = new SetOfShips();
    this.winner = false;
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

  public boolean isWinner() {
    return winner;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setWinner(boolean winner) {
    this.winner = winner;
  }
}
