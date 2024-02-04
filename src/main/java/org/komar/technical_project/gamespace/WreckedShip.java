package org.komar.technical_project.gamespace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;
import org.komar.technical_project.helper.GameElements;

@Data
public class WreckedShip {
  List<Coordinates> wreckedShipCoordinates = new ArrayList<>();
}
