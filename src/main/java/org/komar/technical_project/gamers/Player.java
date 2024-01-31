package org.komar.technical_project.gamers;


import java.util.Scanner;
import org.komar.technical_project.gamespace.Coordinates;
import org.komar.technical_project.gamespace.GameField;
import org.komar.technical_project.gamespace.SetOfShips;
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


  public Player(String name) {
    this.name = name;
    this.gameField = new GameField();
    this.setOfShips = new SetOfShips();
    this.status = GameElements.MISSED;
    this.totalCountShip = setOfShips.getTotalShipsCount();
    this.winner = false;

    this.stepPlayer = true;
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
      if (checkSurroundingElements(row - 1, column)) {
        ConsoleHelper.getMsgHurt();
        getGameField().getGameFieldMatrix()[row - 1][column] = HURT;
        return GameElements.HURT;

      } else {
        ConsoleHelper.getMsgKilled();
        getGameField().getGameFieldMatrix()[row - 1][column] = KILLED;
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

  public boolean checkSurroundingElements(int row,
                                          int col) {
    int rows = getGameField().getGameFieldMatrix().length;
    int cols = getGameField().getGameFieldMatrix()[0].length;
    boolean isChecked = false; // KILLED

    for (int i = row - 1; i <= row + 1; i++) {
      for (int j = col - 1; j <= col + 1; j++) {
        if (i >= 0 && i < rows && j >= 0 && j < cols && (i != row || j != col)) {
          if (getGameField().getGameFieldMatrix()[i][j].equals(ELEMENT_SHIP)) {
            isChecked = true; //HURT
          }
        }
      }
    }
    return isChecked;
  }
}
