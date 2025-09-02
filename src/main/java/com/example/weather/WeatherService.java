package com.example.weather;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class WeatherService {
    private static final HttpClient CLIENT = HttpClient.newBuilder().build();

    public static JsonObject fetchWeatherJson(String city) throws Exception {
        String apiKey = System.getenv("OPENWEATHER_API_KEY");
        if (apiKey == null || apiKey.isBlank()) {
            apiKey = System.getProperty("openweather.apikey");
        }
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException(
                "OpenWeatherMap API key not set. Use environment variable OPENWEATHER_API_KEY or -Dopenweather.apikey=YOUR_KEY"
            );
        }

        String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8);
        String uri = String.format(
            "https://api.openweathermap.org/data/2.5/weather?q=%s&units=metric&appid=%s",
            encodedCity, apiKey
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .GET()
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            try {
                JsonElement elem = JsonParser.parseString(response.body());
                if (elem.isJsonObject() && elem.getAsJsonObject().has("message")) {
                    throw new Exception(elem.getAsJsonObject().get("message").getAsString());
                }
            } catch (Exception ignore) {}
            throw new Exception("API returned status " + response.statusCode() + ": " + response.body());
        }

        return JsonParser.parseString(response.body()).getAsJsonObject();
    }

    public static WeatherData getWeather(String city) throws Exception {
        JsonObject json = fetchWeatherJson(city);

        String name = json.has("name") ? json.get("name").getAsString() : city;

        JsonObject main = json.getAsJsonObject("main");
        double temp = main.get("temp").getAsDouble();
        double feels = main.get("feels_like").getAsDouble();
        int humidity = main.get("humidity").getAsInt();

        double windSpeed = 0.0;
        if (json.has("wind") && json.getAsJsonObject("wind").has("speed")) {
            windSpeed = json.getAsJsonObject("wind").get("speed").getAsDouble();
        }

        JsonObject weather = json.getAsJsonArray("weather").get(0).getAsJsonObject();
        String description = weather.get("description").getAsString();
        String icon = weather.get("icon").getAsString();

        return new WeatherData(name, temp, feels, humidity, windSpeed, description, icon);
    }
}
