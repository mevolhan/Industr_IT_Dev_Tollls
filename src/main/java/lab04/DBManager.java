package lab04;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DBManager {
    private static final String DB_URL = "jdbc:sqlite:src/main/data/words.db";

    public DBManager() {
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        String sql = """
                CREATE TABLE IF NOT EXISTS word_stats (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    word TEXT NOT NULL,
                    frequency INTEGER NOT NULL
                );
                """;
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Ошибка при создании таблицы: " + e.getMessage());
        }
    }

    public void insertWordStats(Map<String, Integer> wordStats) {
        String sql = "INSERT INTO word_stats (word, frequency) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (Map.Entry<String, Integer> entry : wordStats.entrySet()) {
                pstmt.setString(1, entry.getKey());
                pstmt.setInt(2, entry.getValue());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        } catch (SQLException e) {
            System.out.println("Ошибка при вставке в базу: " + e.getMessage());
        }
    }

    public List<WordStat> getTopWords(int limit) {
        List<WordStat> list = new ArrayList<>();
        String sql = "SELECT word, frequency FROM word_stats ORDER BY frequency DESC LIMIT ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, limit);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(new WordStat(rs.getString("word"), rs.getInt("frequency")));
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при выборке из базы: " + e.getMessage());
        }
        return list;
    }
}
