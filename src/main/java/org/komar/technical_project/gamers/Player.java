package org.komar.technical_project.gamers;


import java.util.Scanner;
import org.komar.technical_project.gamespace.Coordinates;
import org.komar.technical_project.gamespace.GameField;
import org.komar.technical_project.gamespace.SetOfShips;
import org.komar.technical_project.gamespace.ShipCoordinates;
import org.komar.technical_project.helper.ConsoleHelper;
import org.komar.technical_project.helper.GameElements;
import org.komar.technical_project.helper.TextColor;

public class Player {

  protected final String ELEMENT_SHIP = GameElements.ONE_ELEMENT.getNameElement();
  protected final String MISSED = TextColor.ANSI_YELLOW.getColorText()
      + GameElements.MISSED.getNameElement() + TextColor.ANSI_BLUE.getColorText();
  protected final String KILLED = TextColor.ANSI_YELLOW.getColorText()
      + GameElements.KILLED.getNameElement() + TextColor.ANSI_BLUE.getColorText();
  protected final String HURT = TextColor.ANSI_YELLOW.getColorText()
      + GameElements.HURT.getNameElement() + TextColor.ANSI_BLUE.getColorText();
  protected final String BUSY = GameElements.BUSY.getNameElement();

  protected String name;
  protected GameField gameField;
  protected SetOfShips setOfShips;
  protected int totalCountShip;

  protected GameElements status;
  protected boolean winner;
  protected boolean stepPlayer;
  protected int countHurtElements;
  protected ShipCoordinates ship;

  public Player(String name) {
    this.name = name;
    this.gameField = new GameField();
    this.setOfShips = new SetOfShips();
    this.status = GameElements.MISSED;
    this.totalCountShip = setOfShips.getTotalShipsCount();
    this.winner = false;

    this.stepPlayer = true;
    this.countHurtElements = 0;

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

  public GameElements getStatus() {
    return status;
  }

  public void setStatus(GameElements status) {
    this.status = status;
  }

  public boolean isWinner() {
    return winner;
  }

  public void setWinner(boolean winner) {
    this.winner = winner;
  }

  public int getCountHurtElements() {
    return countHurtElements;
  }
  public void setCountHurtElements(int countHurtElements) {
    this.countHurtElements = countHurtElements;
  }

  public Coordinates getCoordinates(Scanner scanner) {
    String coordinates = scanner.nextLine();
    String[] partMsg = coordinates.split("-");

    int row = Integer.parseInt(partMsg[0]);
    char columnChar = partMsg[1].charAt(0);
    int column = getGameField().getColumnsNameList().indexOf(columnChar);

    return new Coordinates(row, column);
  }

  public GameElements checkingForHits(int row,
                                      int column) {

    if (getGameField().getGameFieldMatrix()[row - 1][column] == null) {
      ConsoleHelper.getMsgMissed();
      getGameField().getGameFieldMatrix()[row - 1][column] = MISSED;
      return GameElements.MISSED;
    } else if (getGameField().getGameFieldMatrix()[row - 1][column].equals(BUSY)) {
      ConsoleHelper.getMsgMissed();
      getGameField().getGameFieldMatrix()[row - 1][column] = MISSED;
      return GameElements.MISSED;
    } else if (getGameField().getGameFieldMatrix()[row - 1][column].equals(ELEMENT_SHIP)) {
      countHurtElements++;

      if (countHurtElements == 1) {
        ship = new ShipCoordinates(row - 1, column, getGameField().getGameFieldMatrix());
      }
      getGameField().getGameFieldMatrix()[row - 1][column] = HURT;
      ship.getShipCoordinates().put(new Coordinates(row - 1, column), GameElements.HURT);

      if (ship.checkSurroundingElements(row - 1, column)) {
        ConsoleHelper.getMsgHurt();
        return GameElements.HURT;
      } else {
        ConsoleHelper.getMsgKilled();
        return GameElements.KILLED;
      }
    } else if (getGameField().getGameFieldMatrix()[row - 1][column].equals(MISSED)
        || getGameField().getGameFieldMatrix()[row - 1][column].equals(KILLED)) {
      ConsoleHelper.getMsgLoser();
      stepPlayer = false;
      return GameElements.MISSED;
    }
    return null;
  }
}
