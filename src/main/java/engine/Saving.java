package engine;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Saving {

    private Gson gson = new Gson();
    private JsonObject object = new JsonObject();

    public Saving() throws IOException {
    }

    public void save(List<PageEntry> pageEntryList, String text) throws IOException {
        Writer writer = Files.newBufferedWriter(Paths.get("resultOfSaving.json"));
        object.add(text, gson.toJsonTree(pageEntryList));
        gson.toJson(object, writer);
        writer.close();
    }
}
