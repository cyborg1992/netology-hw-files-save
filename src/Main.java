import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        final File dirSaveGame = new File("C:\\Games\\saveGames");
        GameProgress[] gameProgress = {
                new GameProgress(100, 11, 80, 50),
                new GameProgress(80, 15, 50, 30),
                new GameProgress(50, 3, 30, 20)
        };
        if (dirSaveGame.exists()) {
            for (int i = 0; i < gameProgress.length; i++) {
                if (saveGame(dirSaveGame + "\\save" + (i + 1) + ".dat",
                        gameProgress[i])) {
                    System.out.printf("Успешное сохранение в файл save%d.dat%n", i + 1);
                } else {
                    System.out.printf("Не удалось сохранить в файл save%d.dat%n", i + 1);
                }
            }
            File[] listFiles = dirSaveGame.listFiles();
            if (listFiles != null
                    && zipFiles(dirSaveGame + "\\save.zip", listFiles)) {
                System.out.println("Файлы сохранения архивированы в файл save.zip");
                for (File file : listFiles) {
                    if (file.delete()) {
                        System.out.println("Удалён файл " + file.getName());
                    } else {
                        System.out.println("Не удалось удалить файл " + file.getName());
                    }
                }
            } else {
                System.out.println("Файлы сохранения не архивированы");
            }
        } else {
            System.out.println("Папки saveGames не существует");
        }


    }

    public static boolean saveGame(String filePath, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(filePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean zipFiles(String zipPath, File[] files) {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipPath))) {
            for (File file : files) {
                FileInputStream fis = new FileInputStream(file);
                ZipEntry entry = new ZipEntry(file.getName());
                zos.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zos.write(buffer);
                zos.closeEntry();
                fis.close();
            }
            return true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }
}
