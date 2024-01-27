package org.komar.technical_project.bot;

import org.komar.technical_project.gamer1.Player;

public class Bot extends Player {

  public Bot() {
    super("Bot Валера");
    gameField.randomFillGameField(setOfShips.getCompleteSetOfShips());
  }
}
