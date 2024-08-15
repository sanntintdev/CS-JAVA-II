# Weather Information App

## Table of Contents

1. [Introduction](#introduction)
2. [Features](#features)
3. [Requirements](#requirements)
4. [Installation](#installation)
5. [Usage](#usage)
6. [Code Structure](#code-structure)
7. [API Integration](#api-integration)
8. [Troubleshooting](#troubleshooting)
9. [Future Improvements](#future-improvements)

## Introduction

The Weather Information App is a Java-based desktop application that provides real-time weather information for a specified location. It uses the WeatherAPI service to fetch current weather data and displays it in a user-friendly interface.

## Features

- Search for weather information by location
- Display current temperature, humidity, wind speed, and weather condition
- Toggle between Celsius and Fahrenheit temperature units
- Simple and intuitive graphical user interface
- Error handling for invalid inputs and API failures
- Search history tracking with timestamps (last 10 searches)
- Dynamic background changes based on time of day

## Requirements

- Java Development Kit (JDK) 6 or later (compatible with older Java versions)
- WeatherAPI key (sign up at [WeatherAPI.com](https://www.weatherapi.com/))

## Installation

1. Clone or download the repository to your local machine.
2. Open the `WeatherApiService.java` file and replace `"YOUR_API_KEY_HERE"` with your actual WeatherAPI key.
3. Compile the Java files:
   ```
   javac WeatherApp.java WeatherApiService.java
   ```

## Usage

1. Run the application:
   ```
   java WeatherApp
   ```
2. Enter a location (city name, ZIP code, or coordinates) in the text field.
3. Click the "Search" button or press Enter to fetch weather information.
4. The app will display the current weather information for the specified location.
5. Use the radio buttons at the bottom to switch between Celsius and Fahrenheit.
6. View your search history in the panel on the right side of the application.

## Code Structure

The application consists of two main Java classes:

1. `WeatherApp.java`: Contains the main GUI components and application logic.

   - Handles user input and display of weather information
   - Manages search history
   - Implements dynamic background changes

2. `WeatherApiService.java`: Handles API communication and data parsing.
   - Fetches weather data from WeatherAPI
   - Parses JSON responses
   - Implements error handling for API requests

## API Integration

The app uses the WeatherAPI service to fetch real-time weather data. The `WeatherApiService` class encapsulates all API-related functionality.

## Troubleshooting

- If you encounter compilation errors, ensure you're using a compatible Java version (JDK 6 or later).
- If you see an error related to the API key, double-check that you've replaced the placeholder in `WeatherApiService.java` with your actual WeatherAPI key.
- For network-related issues, check your internet connection and firewall settings.

## Future Improvements

- Add weather forecasting functionality
- Implement location autocomplete
- Enhance the UI with weather icons
- Add more detailed error messages and handling

For any issues or suggestions, please open an issue in the project repository.
