import java.io.*;

public class Main {
    public static void main(String[] args) {
        final File dirSaveGame = new File("C:\\Games\\saveGames");
        GameProgress[] gameProgress = {
                new GameProgress(100, 11, 80, 50),
                new GameProgress(80, 15, 50, 30),
                new GameProgress(50, 3, 30, 20)
        };
        File zipFile = new File(dirSaveGame, "save.zip");
        if (dirSaveGame.exists()) {
            for (int i = 0; i < gameProgress.length; i++) {
                if (Serialization.saveGame(dirSaveGame + "\\save" + (i + 1) + ".dat",
                        gameProgress[i])) {
                    System.out.printf("Успешное сохранение в файл save%d.dat%n", i + 1);
                } else {
                    System.out.printf("Не удалось сохранить в файл save%d.dat%n", i + 1);
                }
            }
            File[] listFiles = getSaveFiles(dirSaveGame);
            if (listFiles != null && Zip.zipFiles(zipFile, listFiles)) {
                System.out.println("Файлы сохранения архивированы в файл " + zipFile.getName());
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
        if (Zip.openZip(zipFile, dirSaveGame.getPath())) {
            System.out.println("Архив " + zipFile.getName() + " успешно распакован в директорию " + dirSaveGame);
            File[] listFile = getSaveFiles(dirSaveGame);
            if (listFile != null) {
                System.out.println("Состояния сохранённых игр");
                for (File file : listFile) {
                    GameProgress gp = Serialization.openProgress(file);
                    System.out.println(gp);
                }
            }
        }
    }

    private static File[] getSaveFiles(File dirSaveGame) {
        return dirSaveGame.listFiles(x -> !x.getName().endsWith(".zip"));
    }
}
