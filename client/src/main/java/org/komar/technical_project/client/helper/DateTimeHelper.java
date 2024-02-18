package org.komar.technical_project.client.helper;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateTimeHelper {

  /**
   * Класс, который работает с датами
   */

  private static String formatPattern = "dd-MM-yyyy HH:mm:ss";
  private static String formatTimePattern = "HH:mm:ss";
  private static String formatPatternFused = "yyMMddHHmmss";

  public static DateTimeFormatter getDateTimeFormat() {
    return DateTimeFormatter.ofPattern(formatPattern, Locale.ENGLISH);
  }
  public static DateTimeFormatter getTimeFormat() {
    return DateTimeFormatter.ofPattern(formatTimePattern, Locale.ENGLISH);
  }

  public static DateTimeFormatter getDateTimeFormatFused() {
    return DateTimeFormatter.ofPattern(formatPatternFused, Locale.ENGLISH);
  }
}