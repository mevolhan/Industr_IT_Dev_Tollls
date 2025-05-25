package lab02;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultMerger {
    public static Map<String, Integer> merge(List<Map<String, Integer>> results) {
        Map<String, Integer> merged = new HashMap<>();
        for (Map<String, Integer> result : results) {
            for (Map.Entry<String, Integer> entry : result.entrySet()) {
                merged.put(entry.getKey(), merged.getOrDefault(entry.getKey(), 0) + entry.getValue());
            }
        }
        return merged;
    }
}
