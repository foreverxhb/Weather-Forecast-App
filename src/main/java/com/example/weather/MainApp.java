package com.example.weather;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("Weather Forecast App");

        Label title = new Label("Weather Forecast");
        title.setFont(new Font(20));

        TextField cityField = new TextField();
        cityField.setPromptText("Enter city (e.g. London)");
        Button searchBtn = new Button("Search");

        HBox searchBox = new HBox(8, cityField, searchBtn);
        searchBox.setAlignment(Pos.CENTER_LEFT);

        ImageView iconView = new ImageView();
        iconView.setFitWidth(100);
        iconView.setFitHeight(100);
        iconView.setPreserveRatio(true);

        Label nameLabel = new Label("City: -");
        Label tempLabel = new Label("Temperature: -");
        Label descLabel = new Label("Description: -");
        Label humidityLabel = new Label("Humidity: -");
        Label windLabel = new Label("Wind: -");

        VBox infoBox = new VBox(6, nameLabel, tempLabel, descLabel, humidityLabel, windLabel);
        infoBox.setAlignment(Pos.CENTER_LEFT);

        ProgressIndicator progress = new ProgressIndicator();
        progress.setVisible(false);
        progress.setPrefSize(40, 40);

        HBox resultBox = new HBox(12, iconView, infoBox, progress);
        resultBox.setAlignment(Pos.CENTER_LEFT);

        VBox root = new VBox(12, title, searchBox, resultBox);
        root.setPadding(new Insets(16));

        searchBtn.setOnAction(ev -> {
            String city = cityField.getText().trim();
            if (city.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Please enter a city name.");
                return;
            }

            Task<WeatherData> task = new Task<>() {
                @Override
                protected WeatherData call() throws Exception {
                    return WeatherService.getWeather(city);
                }
            };

            task.setOnRunning(e -> {
                progress.setVisible(true);
                searchBtn.setDisable(true);
                cityField.setDisable(true);
            });

            task.setOnSucceeded(e -> {
                WeatherData data = task.getValue();
                nameLabel.setText("City: " + data.getCityName());
                tempLabel.setText(String.format("Temperature: %.1f °C (feels %.1f °C)", data.getTemp(), data.getFeelsLike()));
                descLabel.setText("Description: " + data.getDescription());
                humidityLabel.setText("Humidity: " + data.getHumidity() + "%");
                windLabel.setText(String.format("Wind: %.1f m/s", data.getWindSpeed()));

                String iconUrl = "https://openweathermap.org/img/wn/" + data.getIconCode() + "@2x.png";
                iconView.setImage(new Image(iconUrl, true));

                progress.setVisible(false);
                searchBtn.setDisable(false);
                cityField.setDisable(false);
            });

            task.setOnFailed(e -> {
                progress.setVisible(false);
                searchBtn.setDisable(false);
                cityField.setDisable(false);
                Throwable ex = task.getException();
                showAlert(Alert.AlertType.ERROR, ex == null ? "Unknown error" : ex.getMessage());
            });

            new Thread(task).start();
        });

        Scene scene = new Scene(root, 520, 220);
        stage.setScene(scene);
        stage.show();
    }

    private void showAlert(Alert.AlertType type, String message) {
        Platform.runLater(() -> {
            Alert a = new Alert(type);
            a.setTitle("Weather App");
            a.setHeaderText(null);
            a.setContentText(message);
            a.showAndWait();
        });
    }

    public static void main(String[] args) {
        launch();
    }
}
