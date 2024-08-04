package dev.fomastree.frontend;

import dev.fomastree.backend.Alpaca;
import dev.fomastree.backend.JSON;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HelloFX extends Application {

    @Override
    public void start(Stage stage) {
        String strategy = "TrendFollowing";
        String share = "AAPL";

        stage.setTitle("Trade-o-matic");

        Button startHistorical = new Button("New historical data");
        startHistorical.setOnAction(e -> Alpaca.getHistoricalData());


        Button trendFollowing = new Button("Trend following");
        trendFollowing.setOnAction(e -> JSON.readBatchedJSON(share, strategy));

        // Button startLive = new Button("Live Trading");
        // startLive.setOnAction(e -> System.out.println("Button Clicked!"));


        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
