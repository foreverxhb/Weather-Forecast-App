package com.example.weather;

public class WeatherData {
    private final String cityName;
    private final double temp;
    private final double feelsLike;
    private final int humidity;
    private final double windSpeed;
    private final String description;
    private final String iconCode;

    public WeatherData(String cityName, double temp, double feelsLike, int humidity,
                       double windSpeed, String description, String iconCode) {
        this.cityName = cityName;
        this.temp = temp;
        this.feelsLike = feelsLike;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.description = description;
        this.iconCode = iconCode;
    }

    public String getCityName() { return cityName; }
    public double getTemp() { return temp; }
    public double getFeelsLike() { return feelsLike; }
    public int getHumidity() { return humidity; }
    public double getWindSpeed() { return windSpeed; }
    public String getDescription() { return description; }
    public String getIconCode() { return iconCode; }
}
