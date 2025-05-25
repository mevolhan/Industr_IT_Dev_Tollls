package lab01;

import core.TextProcessor;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;

public class main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Использование: java Runner <путь_к_файлу> [слово]");
            return;
        }

        String filePath = args[0];
        String targetWord = args.length > 1 ? args[1] : null;

        try {
            TextProcessor processor = new TextProcessor("src/main/data/forbidden_words.txt");
            Map<String, Integer> wordStats = processor.analyzeFile(filePath);

            System.out.println("Статистика по словам:");
            wordStats.entrySet()
                    .stream()
                    .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                    .limit(10)
                    .forEach(entry -> System.out.printf("%s : %d%n", entry.getKey(), entry.getValue()));

            if (targetWord != null) {
                int count = wordStats.getOrDefault(targetWord.toLowerCase(), 0);
                System.out.printf("Слово \"%s\" встречается %d раз(а)%n", targetWord, count);
            }

        } catch (IOException e) {
            System.out.println("Ошибка при обработке файла: " + e.getMessage());
        }
    }
}

//java lab01.main src/main/data/files/text1.txt - запуск