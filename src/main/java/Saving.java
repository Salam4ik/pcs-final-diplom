import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Saving {
    private Writer writer = Files.newBufferedWriter(Paths.get("resultOfSaving.json"));

    private Gson gson = new Gson();

    public Saving() throws IOException {
    }

    public void Save(List<PageEntry> pageEntryList) throws IOException {
        gson.toJson(pageEntryList, writer);
        writer.close();
    }
}
