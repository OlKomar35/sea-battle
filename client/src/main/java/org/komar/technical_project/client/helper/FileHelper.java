package org.komar.technical_project.client.helper;

public class FileHelper {

  /**
   * Класс для работы с файлами и директориями
   */
  public static String rootDir = System.getProperty("user.dir");
  public static String fileSeparator = System.getProperty("file.separator");

  public static String getRootDirPath() {
    return rootDir + fileSeparator + "sea_battle_games" ;
  }

  public static String getArchiveDirPath() {
    return rootDir + fileSeparator + "archive_games" ;
  }

}
