package lab02;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        String folderPath = "src/main/data/files";
        String stopWordsPath = "src/main/data/blacklist.txt";

        // Чтение стоп-слов
        Set<String> stopWords = readStopWords(stopWordsPath);
        if (stopWords == null) return;

        File folder = new File(folderPath);
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".txt"));
        if (files == null || files.length == 0) {
            System.out.println("Нет файлов для анализа.");
            return;
        }

        ExecutorService executor = Executors.newFixedThreadPool(files.length);
        List<Future<Map<String, Integer>>> futures = new ArrayList<>();

        for (File file : files) {
            futures.add(executor.submit(new FileAnalyzer(file.getPath(), stopWords)));
        }

        List<Map<String, Integer>> results = new ArrayList<>();
        for (Future<Map<String, Integer>> future : futures) {
            try {
                results.add(future.get());
            } catch (InterruptedException | ExecutionException e) {
                System.out.println("Ошибка выполнения потока: " + e.getMessage());
            }
        }

        executor.shutdown();

        // Объединение результатов
        Map<String, Integer> finalStats = ResultMerger.merge(results);

        System.out.println("Общая статистика слов:");
        finalStats.entrySet().stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .limit(15)
                .forEach(entry -> System.out.printf("%s : %d%n", entry.getKey(), entry.getValue()));
    }

    private static Set<String> readStopWords(String filePath) {
        Set<String> stopWords = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String word;
            while ((word = reader.readLine()) != null) {
                stopWords.add(word.trim().toLowerCase());
            }
        } catch (Exception e) {
            System.out.println("Не удалось прочитать файл стоп-слов: " + e.getMessage());
            return null;
        }
        return stopWords;
    }
}
