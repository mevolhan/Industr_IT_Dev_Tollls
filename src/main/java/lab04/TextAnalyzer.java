package lab04;

import java.util.*;
import java.util.concurrent.*;

public class TextAnalyzer {
    private final ExecutorService executor;

    public TextAnalyzer(int threads) {
        this.executor = Executors.newFixedThreadPool(threads);
    }

    public Map<String, Integer> analyze(List<String> texts) {
        List<Future<Map<String, Integer>>> futures = new ArrayList<>();

        for (String text : texts) {
            futures.add(executor.submit(() -> processText(text)));
        }

        Map<String, Integer> finalResult = new HashMap<>();
        for (Future<Map<String, Integer>> future : futures) {
            try {
                Map<String, Integer> partial = future.get();
                for (var entry : partial.entrySet()) {
                    finalResult.merge(entry.getKey(), entry.getValue(), Integer::sum);
                }
            } catch (InterruptedException | ExecutionException e) {
                System.out.println("Ошибка в потоке анализа: " + e.getMessage());
            }
        }

        executor.shutdown();
        return finalResult;
    }

    private Map<String, Integer> processText(String text) {
        Map<String, Integer> map = new HashMap<>();
        String[] words = text.toLowerCase().split("\\W+");
        for (String word : words) {
            if (!word.isBlank()) {
                map.merge(word, 1, Integer::sum);
            }
        }
        return map;
    }
}
