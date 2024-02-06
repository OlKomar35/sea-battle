package org.komar.technical_project.helper;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateTimeHelper {

  private static String formatPattern = "dd-MM-yyyy HH:mm:ss";
  private static String formatTimePattern = "HH:mm:ss";
  private static String formatPatternFused = "yyMMddHHmmss";

  public static String getDateFormat(long date) {
    return Instant.ofEpochMilli(date).atZone(ZoneId.systemDefault()).format(getDateTimeFormat()).toString();
  }

  public static String getDateFormatFused(long date) {
    return Instant.ofEpochMilli(date).atZone(ZoneId.systemDefault()).format(getDateTimeFormatFused()).toString();
  }

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