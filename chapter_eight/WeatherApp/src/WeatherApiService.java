import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class WeatherApiService {
    private static final String API_KEY = "b1b57b887ed24ecbbd8133118241508";
    private static final String API_URL = "http://api.weatherapi.com/v1/current.json";

    public Map getWeatherData(String location) throws Exception {
        String jsonResponse = fetchData(location);
        if (jsonResponse.contains("error")) {
            throw new Exception(extractErrorMessage(jsonResponse));
        }
        return parseJson(jsonResponse);
    }

    private String fetchData(String location) throws Exception {
        String urlString = API_URL + "?key=" + API_KEY + "&q=" + location;
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } else {
            throw new Exception("HTTP error code: " + responseCode);
        }
    }

    private Map parseJson(String json) {
        Map result = new HashMap();
        result.put("temp_c", extractNumericValue(json, "temp_c"));
        result.put("temp_f", extractNumericValue(json, "temp_f"));
        result.put("humidity", extractNumericValue(json, "humidity"));
        result.put("wind_kph", extractNumericValue(json, "wind_kph"));
        result.put("condition", extractStringValue(json, "text"));
        return result;
    }

    private String extractStringValue(String json, String key) {
        int keyIndex = json.indexOf("\"" + key + "\"");
        int colonIndex = json.indexOf(":", keyIndex);
        int valueStart = json.indexOf("\"", colonIndex) + 1;
        int valueEnd = json.indexOf("\"", valueStart);
        return json.substring(valueStart, valueEnd);
    }

    private String extractNumericValue(String json, String key) {
        int keyIndex = json.indexOf("\"" + key + "\"");
        int colonIndex = json.indexOf(":", keyIndex);
        int valueStart = colonIndex + 1;
        int valueEnd = json.indexOf(",", valueStart);
        if (valueEnd == -1) { // if it's the last value in a block
            valueEnd = json.indexOf("}", valueStart);
        }
        return json.substring(valueStart, valueEnd).trim();
    }

    private String extractErrorMessage(String json) {
        try {
            int start = json.indexOf("\"message\":\"") + 11;
            int end = json.indexOf("\"", start);
            return json.substring(start, end);
        } catch (Exception e) {
            return "Unknown error occurred";
        }
    }
}