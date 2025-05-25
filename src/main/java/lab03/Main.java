package lab03;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        DBManager db = new DBManager();

        // Примерные данные (будут заменены реальными)
        Map<String, Integer> sampleData = new HashMap<>();
        sampleData.put("java", 10);
        sampleData.put("code", 7);
        sampleData.put("thread", 5);
        sampleData.put("file", 9);

        // Сохранение в БД
        db.insertWordStats(sampleData);

        // Получение топ-3
        List<WordStat> topWords = db.getTopWords(3);

        System.out.println("Топ 3 слов по частоте:");
        for (WordStat word : topWords) {
            System.out.printf("%s : %d\n", word.getWord(), word.getFrequency());
        }
    }
}
