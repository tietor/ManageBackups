import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ManageBackups {

  private final static String PATH = "/home/tie/Documents/fabio/Notenerfassungstool/Backup-Database/";
  private static String dateNow = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now());
  private static Calendar calendar = Calendar.getInstance();


  public static void main(String[] args) {
    renameFile();
    File folder = new File(PATH);
    File[] listOfFiles = folder.listFiles();
    if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
      ArrayList<String> filesToZip = new ArrayList<>();
      for (File listOfFile : listOfFiles) {
        if (listOfFile.isFile()) {
          if (!listOfFile.getName().endsWith(".zip") && !listOfFile.getName().endsWith(".jar") && !listOfFile.getName().endsWith(".log"))
            filesToZip.add(listOfFile.getName());
        }
      }
      writeToZipFile(filesToZip);
    }
  }

  private static void writeToZipFile(List<String> filesToZip) {
    try {
      FileOutputStream fileOutputStream = new FileOutputStream(
          PATH + "Week-" + (calendar.get(Calendar.WEEK_OF_YEAR) - 1) + "-" + calendar.get(Calendar.YEAR)
              + "_BackupDB.zip");
      ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
      for (String file : filesToZip) {
        File fileToZip = new File(PATH + file);
        FileInputStream fileInputStream = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
        zipOutputStream.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fileInputStream.read(bytes)) >= 0) {
          zipOutputStream.write(bytes, 0, length);
        }
        fileInputStream.close();
        fileToZip.delete();
      }
      zipOutputStream.close();
      fileOutputStream.close();
      System.out.println(dateNow + " All Files were successfully zipped.");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void renameFile() {
    final String EXTENSION = ".sql";
    File file = new File(PATH + "backupDB" + EXTENSION);
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String newFileName = PATH + "backupDB_" + dateTimeFormatter.format(LocalDate.now().minusDays(1)) + EXTENSION;
    File newFile = new File(newFileName);
    if (file.isFile()) {
      System.out.println(dateNow + " File is renamed: " + file.renameTo(newFile));
    } else {
      System.out.println(dateNow + " Couldn't found file!");
    }
  }
}
