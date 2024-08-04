package dev.fomastree.backend;

import com.google.gson.JsonElement;
import com.google.gson.JsonStreamParser;

import dev.fomastree.strategies.Strategy;
import dev.fomastree.strategies.StrategyFactory;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for writing JSON data to a file.
 */
public class JSON {

    /**
     * Reads JSON data and runs strategy on each batch.
     *
     * @param filePath The file path where the JSON data will be written.
     */
    public static void readBatchedJSON(String share, String strategy) {
        final int BATCH_SIZE = 1000;
        
        try (FileReader fileReader = new FileReader("historical-data/" + share + ".json")) {
            JsonStreamParser parser = new JsonStreamParser(fileReader);

            List<Double> currentBatch = new ArrayList<>();
            int count = 0;

            while (parser.hasNext()) {
                JsonElement element = parser.next();
                double value = element.getAsDouble();
                currentBatch.add(value);
                count++;

                if (count == BATCH_SIZE) {
                    Strategy strategyInstance = new StrategyFactory().useStrategy(strategy);
                    strategyInstance.execute(currentBatch);
                    currentBatch.clear();
                    count = 0;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Writes JSON data to the specified file path.
     *
     * @param jsonData The JSON data to write.
     * @param filePath The file path where the JSON data will be written.
     */
    public static void write(String jsonData, String share) {
        if (jsonData == null) {
            throw new IllegalArgumentException(
                    "JSON data and file path cannot be null.");
        }

        try (FileWriter fileWriter = new FileWriter("historical-data/" + share + ".json")) {
            fileWriter.write(jsonData);
        } catch (IOException e) {
            System.err.println(
                    "Error writing JSON data to file: " + e.getMessage());
        }
    }
}
