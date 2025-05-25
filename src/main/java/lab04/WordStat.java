package lab04;

public class WordStat {
    private final String word;
    private final int frequency;

    public WordStat(String word, int frequency) {
        this.word = word;
        this.frequency = frequency;
    }

    public String getWord() {
        return word;
    }

    public int getFrequency() {
        return frequency;
    }
}
