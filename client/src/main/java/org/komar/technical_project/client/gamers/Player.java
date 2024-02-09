package org.komar.technical_project.client.gamers;


import java.util.Scanner;
import org.komar.technical_project.client.gamespace.Coordinates;
import org.komar.technical_project.client.gamespace.GameField;
import org.komar.technical_project.client.gamespace.SetOfShips;
import org.komar.technical_project.client.gamespace.ShipCoordinates;
import org.komar.technical_project.client.helper.ConsoleHelper;
import org.komar.technical_project.client.helper.GameElements;
import org.komar.technical_project.client.helper.TextColor;

public class Player {

  //region Переменные класса Player
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
  protected int countSteps;
  protected int countFineSteps;
  //endregion

  public Player() {

    this.name = "Без имени игрок";

    this.gameField = new GameField();
    this.setOfShips = new SetOfShips();
    this.status = GameElements.MISSED;
    this.totalCountShip = setOfShips.getTotalShipsCount();
    this.winner = false;

    this.ship = new ShipCoordinates();

    this.stepPlayer = true;
    this.countHurtElements = 0;

    this.countSteps = 0;
    this.countFineSteps = 0;
  }

  //region Геттеры и сеттеры
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

  public int getCountSteps() {
    return countSteps;
  }

  public void setCountSteps(int countSteps) {
    this.countSteps = countSteps;
  }

  public int getCountFineSteps() {
    return countFineSteps;
  }

  public void setCountFineSteps(int countFineSteps) {
    this.countFineSteps = countFineSteps;
  }

  public ShipCoordinates getShip() {
    return ship;
  }

  public void setShip(ShipCoordinates ship) {
    this.ship = ship;
  }

  //endregion

  /**
   * @param scanner переменная для чтения данных из консоли
   * @return Возвращает координаты для выстрела
   */
  public Coordinates getCoordinates(Scanner scanner) {
    int row = 0;
    int column = 0;
    boolean flag = true;
    while (flag) {
      String coordinates = scanner.nextLine();
      String[] partMsg = coordinates.split("-");
      row = Integer.parseInt(partMsg[0]);
      if (row > 0 && row <= 16) {
        flag = false;
      }
      char columnChar = partMsg[1].charAt(0);
      try {
        column = getGameField().getColumnsNameList().indexOf(columnChar);
        flag = false;
      } catch (Exception e) {
        flag = true;
      }
      if (!flag) {
        System.out.println("Введены не верные координаты, введите заново");
      }
    }

    return new Coordinates(row, column);
  }

  /**
   * @param row    координата строки
   * @param column координата столбца
   * @return Возвращает один из элементов из перечисления GameElements, проставляет в матрице с кораблями значение из
   * GameElements
   */

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
        this.setShip(new ShipCoordinates(row - 1, column, getGameField().getGameFieldMatrix()));
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
