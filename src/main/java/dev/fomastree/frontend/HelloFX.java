package dev.fomastree.frontend;

import com.google.gson.JsonArray;

import dev.fomastree.backend.Alpaca;
import dev.fomastree.backend.JSON;
import dev.fomastree.strategies.StrategyFactory;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HelloFX extends Application {
    private Scene mainScene;

    @Override
    public void start(Stage stage) {

        stage.setTitle("Trade-o-matic");

        // Main Page
        Button liveButton = new Button("Live Data");
        Button historicalButton = new Button("Historical Data");

        VBox mainPage = new VBox();
        mainPage.getChildren().addAll(liveButton, historicalButton);
        mainPage.setAlignment(Pos.CENTER);

        liveButton.setOnAction(e -> System.out.println("Live Data button clicked"));
        historicalButton.setOnAction(e -> showHistoricalSubpage(stage));

        mainScene = new Scene(mainPage, 300, 200);
        stage.setScene(mainScene);
        stage.show();
    }

    private void showHistoricalSubpage(Stage stage) {
        String symbol = "AAPL";

        Button trendFollowingButton = new Button("Trend Following");
        Button bollingerButton = new Button("Bollinger");
        Button getHistoricalDataButton = new Button("New historical data");
        Button backButton = new Button("Back");

        HBox buttonsBox = new HBox(10, trendFollowingButton, bollingerButton, getHistoricalDataButton);
        HBox backButtonBox = new HBox(backButton);
        backButtonBox.setAlignment(Pos.TOP_RIGHT);

        StackPane buttonsBoxPane = new StackPane();
        buttonsBoxPane.getChildren().add(buttonsBox);
        buttonsBoxPane.setAlignment(Pos.CENTER);

        VBox historicalSubpage = new VBox();
        historicalSubpage.getChildren().addAll(buttonsBoxPane, backButtonBox);

        getHistoricalDataButton.setOnAction(e -> {
            System.out.println("Get Historical Data button clicked");
            Alpaca.getHistoricalData(symbol);
        });

        trendFollowingButton.setOnAction(e -> {
            System.out.println("Trend Following button clicked");
            JSON.readJSON(symbol, "TrendFollowing");
        });

        bollingerButton.setOnAction(e -> {
            System.out.println("Bollinger button clicked");
            JsonArray array = JSON.readJSON(symbol, "bars");
            StrategyFactory strategyFactory = new StrategyFactory();
            strategyFactory.useStrategy("Bollinger").backTest(array, symbol);

        });

        backButton.setOnAction(e -> stage.setScene(mainScene));

        Scene historicalScene = new Scene(historicalSubpage, 450, 200);
        stage.setScene(historicalScene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
