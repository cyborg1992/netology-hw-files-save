import java.io.File;

public class Main {
    public static void main(String[] args) {
        GameProgress[] gameProgress = {
                new GameProgress(100, 11, 80, 50),
                new GameProgress(80, 15, 50, 30),
                new GameProgress(50, 3, 30, 20)
        };

        File saveGamePath = new File("C:\\Games\\saveGames");

        if (saveGamePath.exists()) {
            for (int i = 0; i < gameProgress.length; i++) {
                if (saveGame(saveGamePath.getPath() + "\\save" + (i + 1) + ".dat",
                        gameProgress[i])) {
                    System.out.printf("Успешное сохранение в файл save%d.dat%n", i + 1);
                } else {
                    System.out.printf("Не удалось сохранить в файл save%d.dat%n", i + 1);
                }

            }
            if (zipFiles(saveGamePath.getPath() + "\\save.zip",
                    saveGamePath.listFiles())) {
                System.out.println("Успешно запаковано в архив save.zip");
            } else {
                System.out.println("Не удалось архивировать файлы сохранения");
            }
        } else {
            System.out.println("Папки saveGames не существует");
        }


    }

    public static boolean saveGame(String filePathName, GameProgress gameProgress) {
        //TODO saveGame()
        return false;
    }

    public static boolean zipFiles(String zipPathName, File[] files) {
        //TODO zipFiles()
        return false;
    }
}
