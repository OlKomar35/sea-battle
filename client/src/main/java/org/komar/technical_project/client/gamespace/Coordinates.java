package org.komar.technical_project.client.gamespace;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс для хранения координат (row принимает значения от 0 до размера поля -1,
 * а column - числовое значение соответствующее, порядковому номеру буквы, счет начинается с 0)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coordinates {
  int row;
  int column;
}
