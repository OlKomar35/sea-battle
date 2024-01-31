package org.komar.technical_project.gamespace;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coordinates {
  int row;
  int column;
  Orientation orientation = Orientation.NONE;

  public Coordinates(int row,
                     int column) {
    this.row = row;
    this.column = column;
  }
}
