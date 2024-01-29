package org.komar.technical_project.gamers;

public class Bot extends Player {

  public Bot() {
    super("Bot Валера");
    gameField.randomFillGameField(setOfShips.getCompleteSetOfShips());
  }
}
