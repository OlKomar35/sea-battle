package org.komar.technical_project.client.helper;

public class FileHelper {

  public static String rootDir = System.getProperty("user.dir");
  public static String fileSeparator = System.getProperty("file.separator");

  public static String getRootDirPath() {
    System.out.println("Список сыгранных игр:");
    return rootDir + fileSeparator + "sea_battle_games" ;
  }

  public static String getArchiveDirPath() {
    System.out.println("Список заархивированных игр:");
    return rootDir + fileSeparator + "archive_games" ;
  }

}
