import com.fasterxml.jackson.annotation.JsonProperty;

public class OpenWeatherResponse {
    @JsonProperty("weather")
    public Weather[] weather;

    @JsonProperty("main")
    public Main main;


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
    }


    
}