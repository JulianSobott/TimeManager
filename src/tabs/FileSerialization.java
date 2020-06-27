package tabs;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileSerialization<T> {
    protected T data;
    private final Path filePath;

    // TODO: get

    protected FileSerialization(Path filePath) {
        this.filePath = filePath;
    }

    public void save() {
        Gson gson = new Gson();
        String json = gson.toJson(data);
        System.out.println(filePath);
        try {
            Files.write(filePath, json.getBytes());
            System.out.println("Saved config");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LoadResult load() {
        Gson gson = new Gson();
        StringBuilder jsonBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(filePath)) {
            stream.forEach(jsonBuilder::append);
        } catch (IOException e) {
            //
        }
        String json = jsonBuilder.toString();
        try {
            data = (T) gson.fromJson(json, data.getClass());
        }catch (JsonSyntaxException e) {
            e.printStackTrace();
            return LoadResult.FORMAT_ERROR;
        }
        return LoadResult.LOADED;
    }

    public static enum LoadResult {
        LOADED, NO_SUCH_FILE, FORMAT_ERROR
    }

}
