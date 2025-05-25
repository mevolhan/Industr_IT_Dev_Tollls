package lab02;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;

public class FileAnalyzer implements Callable<Map<String, Integer>> {
    private final String filePath;
    private final Set<String> stopWords;

    public FileAnalyzer(String filePath, Set<String> stopWords) {
        this.filePath = filePath;
        this.stopWords = stopWords;
    }

    @Override
    public Map<String, Integer> call() throws Exception {
        Map<String, Integer> wordCount = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.toLowerCase().split("\\W+");
                for (String word : words) {
                    if (word.isEmpty() || stopWords.contains(word)) continue;
                    wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + filePath);
        }

        return wordCount;
    }
}
