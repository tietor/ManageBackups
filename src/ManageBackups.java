import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ManageBackups {

  public static void main(String[] args) {
    final String PATH = "/home/tie/Documents/fabio/FoodApplication/Backup-Database/";
    final String EXTENSION = ".sql";
    File file = new File(PATH + "backupDB" + EXTENSION);
    LocalDate date = LocalDate.now().minusDays(1);
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String filename = PATH + "backupDB_" + dateTimeFormatter.format(date) + EXTENSION;
    File newFileName = new File(filename);
    String dateNow = dateTimeFormatter.format(LocalDate.now());
    if (file.isFile()) {
      System.out.println(dateNow + " File is renamed: " + file.renameTo(newFileName));
    } else {
      System.out.println(dateNow + " Couldn't found file!");
    }
    String fileToDeleteName = PATH + "backupDB_" + dateTimeFormatter.format(LocalDate.now().minusDays(8)) + EXTENSION;
    File fileToDelete = new File(fileToDeleteName);
    if (fileToDelete.isFile()) {
      System.out.println(dateNow + " File is deleted: " + fileToDelete.delete());
    }
  }
}
