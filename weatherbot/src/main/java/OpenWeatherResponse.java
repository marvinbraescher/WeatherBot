import com.fasterxml.jackson.annotation.JsonProperty;

public class OpenWeatherResponse {
    @JsonProperty("weather")
    public Weather[] weather;

    @JsonProperty("main")
    public Main main;

    @JsonProperty("rain")
    public Rain rain;

    public static class Weather {
        @JsonProperty("description")
        public String description;
    }

    public static class Main {
        @JsonProperty("temp")
        public double temp;

        @JsonProperty("temp_min")
        public double tempMin;

        @JsonProperty("temp_max")
        public double tempMax;

        @JsonProperty("humidity")
        public int humidity;
    }

    public static class Rain {
        @JsonProperty("1h")
        public double rain1h;

        @JsonProperty("3h")
        public double rain3h;
    }
}