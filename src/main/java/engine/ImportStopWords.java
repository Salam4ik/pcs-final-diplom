package engine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImportStopWords {
    private List<String> stopWords = new ArrayList<>();
    private BufferedReader reader = new BufferedReader(new FileReader("stop-ru.txt"));


    public List<String> getStopWord() {
        return stopWords;
    }

    public ImportStopWords() throws IOException {
        while (reader.readLine() != null) {
            stopWords.add(reader.readLine());
        }
    }
}
