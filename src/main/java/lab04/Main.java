package lab04;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // 1. Загрузка текстов
        FileLoader loader = new FileLoader("src/main/data/files");
        List<String> texts = loader.loadTexts();

        // 2. Анализ многопоточно
        TextAnalyzer analyzer = new TextAnalyzer(3);
        Map<String, Integer> wordStats = analyzer.analyze(texts);

        // 3. Сохранение в БД
        DBManager db = new DBManager();
        db.insertWordStats(wordStats);

        // 4. Вывод ТОП-5 слов
        List<WordStat> topWords = db.getTopWords(5);
        System.out.println("Топ 5 слов по частоте:");
        for (WordStat word : topWords) {
            System.out.printf("%s : %d\n", word.getWord(), word.getFrequency());
        }
    }
}
