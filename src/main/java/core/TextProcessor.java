package core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class TextProcessor {
    private final Set<String> bannedWords;

    public TextProcessor(String blacklistPath) throws IOException {
        this.bannedWords = new HashSet<>(Files.readAllLines(Path.of(blacklistPath)));
    }

    public Map<String, Integer> analyzeFile(String filePath) throws IOException {
        List<String> lines = Files.readAllLines(Path.of(filePath));
        Map<String, Integer> frequencyMap = new HashMap<>();

        for (String line : lines) {
            String[] words = line.toLowerCase().split("[\\W\\d_]+");

            for (String word : words) {
                if (word.isEmpty() || bannedWords.contains(word)) continue;
                frequencyMap.put(word, frequencyMap.getOrDefault(word, 0) + 1);
            }
        }

        return frequencyMap;
    }
}