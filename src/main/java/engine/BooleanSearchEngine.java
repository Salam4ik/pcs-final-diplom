package engine;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class BooleanSearchEngine implements SearchEngine {
    private List<File> pdfs = new ArrayList<>();
    private Map<String, List<PageEntry>> infoForSearchWord = new HashMap<>();
    private Map<String, Integer> wordOnPage;
    private Set<String> inputText = new HashSet<>();
    private List<PageEntry> searchingList = new ArrayList<>();
    private ImportStopWords stopWords = new ImportStopWords();

    public BooleanSearchEngine(File pdfsDir) throws IOException {
        for (File pdf : pdfsDir.listFiles()) {
            pdfs.add(pdf);
        }
        for (File pdf : pdfs) {
            var doc = new PdfDocument(new PdfReader(pdf));
            for (int i = 0; i < doc.getNumberOfPages(); i++) {
                var text = PdfTextExtractor.getTextFromPage(doc.getPage(i + 1));
                var words = text.split("\\P{IsAlphabetic}+");
                wordOnPage = new HashMap<>();
                for (var word : words) {
                    if (word.isEmpty()) {
                        continue;
                    }
                    word = word.toLowerCase();
                    wordOnPage.put(word, wordOnPage.getOrDefault(word, 0) + 1);
                }
                for (var word : wordOnPage.keySet()) {
                    if (infoForSearchWord.containsKey(word)) {
                        List<PageEntry> pageEntryList = infoForSearchWord.remove(word);
                        pageEntryList.add(new PageEntry(pdf.getName(), i + 1, wordOnPage.get(word)));
                        infoForSearchWord.put(word, pageEntryList);
                    } else {
                        List<PageEntry> pageEntryList = new ArrayList<>();
                        pageEntryList.add(new PageEntry(pdf.getName(), i + 1, wordOnPage.get(word)));
                        infoForSearchWord.put(word, pageEntryList);
                    }
                }
            }
        }
        for (var list : infoForSearchWord.values()) {
            Collections.sort(list);
        }
    }

    @Override
    public List<PageEntry> search(String text) {
        inputText.clear();
        var words = text.split("\\P{IsAlphabetic}+");
        for (var word : words) {
            if (word.isEmpty()) {
                continue;
            }
            word = word.toLowerCase();
            inputText.add(word);
        }
        for (var w : stopWords.getStopWord()) inputText.remove(w);
        if (inputText.size() == 1) {
            searchingList = infoForSearchWord.get(text.toLowerCase());
        } else {
            List<Integer> pageEntryForDelete = new ArrayList<>();
            for (var word : inputText) searchingList.addAll(infoForSearchWord.get(word));
            for (int i = 0; i < (searchingList.size() - 1); i++) {
                pageEntryForDelete.clear();
                for (int i1 = (1 + i); i1 < searchingList.size(); i1++) {
                    if (searchingList.get(i).getPdf().equals(searchingList.get(i1).getPdf()) && searchingList.get(i).getPage() == searchingList.get(i1).getPage()) {
                        searchingList.get(i).setCount(searchingList.get(i).getCount() + searchingList.get(i1).getCount());
                        pageEntryForDelete.add(i1);
                    }
                }
                Collections.reverse(pageEntryForDelete);
                for (int index : pageEntryForDelete) {
                    searchingList.remove(index);
                }
            }
            Collections.sort(searchingList);
        }
        return searchingList;
    }
}
