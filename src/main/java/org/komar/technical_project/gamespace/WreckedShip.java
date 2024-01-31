package org.komar.technical_project.gamespace;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WreckedShip {
  List<Coordinates> wreckedShipCoordinates = new ArrayList<>();
}
