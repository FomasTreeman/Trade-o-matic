package dev.fomastree.backend;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Utility class for writing JSON data to a file.
 */
public class JSON {

    /**
     * Reads JSON data and runs strategy on each batch.
     *
     * @param filePath The file path where the JSON data will be written.
     */
    public static JsonArray readJSON(String symbol, String key) {

        try {
            // create Gson instance
            Gson gson = new Gson();

            // create a reader
            BufferedReader reader = Files.newBufferedReader(Paths.get("historical-data/" + symbol + ".json"));

            // convert JSON file to map
            JsonElement jsonElement = gson.fromJson(reader, JsonElement.class);

            if (jsonElement.isJsonObject()) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                JsonArray array = jsonObject.getAsJsonArray(key);
                
                return array;
            }

            // close reader
            reader.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return new JsonArray();
    }

    /**
     * Writes JSON data to the specified file path.
     *
     * @param jsonData The JSON data to write.
     * @param filePath The file path where the JSON data will be written.
     */
    public static void write(String jsonData, String filepath) {
        if (jsonData == null) {
            throw new IllegalArgumentException(
                    "JSON data and file path cannot be null.");
        }

        try (FileWriter fileWriter = new FileWriter(filepath + ".json", false)) {
            fileWriter.write(jsonData);
        } catch (IOException e) {
            System.err.println(
                    "Error writing JSON data to file: " + e.getMessage());
        }
    }
}
