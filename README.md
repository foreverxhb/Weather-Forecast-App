# Weather Forecast App

A simple JavaFX application that fetches and displays real-time weather information using the OpenWeatherMap API
.

## Features

Search weather by city name

Display:

Temperature (°C)

Feels-like temperature

Humidity

Wind speed

Weather condition + icon

Handles errors like invalid city input

Built with Java 17+, JavaFX, and Gson

## Tech Stack

Java JDK24

JavaFX 20 (via Maven dependencies)

Maven (build & dependency management)

Gson (for JSON parsing)

OpenWeatherMap API


## API Key Setup

This project requires a free API key from OpenWeatherMap
.

You can provide it in two ways:

Option 1 — Environment Variable 
$env:OPENWEATHER_API_KEY="put your api key here"

Option 2 — Maven property at runtime
mvn "-Dopenweather.apikey=put your api ke here" javafx:run

## How to Run
1️. Clone the repo
git clone https://github.com/foreverxhb/weather-forecast-app.git
cd weather-forecast-app

2️. Build the project
mvn clean install

3️. Run the app
# Using env variable
$env:OPENWEATHER_API_KEY="put your api key here"
mvn javafx:run


or

# Passing API key directly
mvn "-Dopenweather.apikey=put_your_api_key_here" javafx:run

## Running in VS Code

Install Extension Pack for Java and Maven for Java.

Open the project folder in VS Code (File → Open Folder).

In the Maven panel (M icon), expand:
weather-forecast-app → Plugins → javafx → javafx:run

Right-click → Run.

(Optional: you can configure .vscode/launch.json to run Maven automatically.)

## Screenshots
![screenshot](https://github.com/foreverxhb/Weather-Forecast-App/blob/579bda603bf9405968ba07d4638d291d9bd09c14/Screenshot%202025-09-02%20183117.png)
![screenshot](https://github.com/foreverxhb/Weather-Forecast-App/blob/579bda603bf9405968ba07d4638d291d9bd09c14/Screenshot%202025-09-02%20183141.png)


## Troubleshooting

Error: JavaFX runtime components are missing → Make sure you run with mvn javafx:run instead of just java MainApp.

401 Unauthorized → Your API key is invalid or not set correctly.

