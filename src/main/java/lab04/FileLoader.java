package lab04;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FileLoader {
    private final String folderPath;

    public FileLoader(String folderPath) {
        this.folderPath = folderPath;
    }

    public List<String> loadTexts() {
        List<String> texts = new ArrayList<>();
        File folder = new File(folderPath);

        File[] files = folder.listFiles((dir, name) -> name.endsWith(".txt"));
        if (files == null) return texts;

        for (File file : files) {
            try {
                texts.add(Files.readString(file.toPath()));
            } catch (IOException e) {
                System.out.println("Не удалось прочитать файл: " + file.getName());
            }
        }
        return texts;
    }
}
