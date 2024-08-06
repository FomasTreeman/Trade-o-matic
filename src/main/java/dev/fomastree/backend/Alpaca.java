package dev.fomastree.backend;

import java.io.IOException;
// import java.time.LocalDateTime;
// import java.time.format.DateTimeFormatter;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Alpaca {
    public static void getHistoricalData(String symbol) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(
                        "https://data.alpaca.markets/v2/stocks/" + symbol.toLowerCase()
                                + "/bars?timeframe=1D&start=2023-08-01T00%3A00%3A00Z&end=2024-08-01T00%3A00%3A00Z&limit=1000&adjustment=raw&feed=sip&currency=GBP&sort=asc")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("APCA-API-KEY-ID", System.getenv("APCA_API_KEY_ID"))
                .addHeader(
                        "APCA-API-SECRET-KEY",
                        System.getenv("APCA_API_SECRET_KEY"))
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                System.out.println(
                        "Request was successful. " + response.code());
                String jsonString = response.body().string();
                JSON.write(jsonString, "historical-data/" + symbol);
                System.out.println("Wrote to file");
            } else {
                System.out.println(
                        "Request was not successful. Response code: " +
                                response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            client.dispatcher().executorService().shutdown();
            client.connectionPool().evictAll();
            System.out.println("Closed connection");
        }
    }

    // private static class DateTimeUtils {
    // public static String getCurrentDateTimeSmallFormat() {
    // LocalDateTime currentDateTime = LocalDateTime.now();
    // DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
    // "MM-dd-yy-HH:mm:ss");
    // return currentDateTime.format(formatter);
    // }
    // }
}
