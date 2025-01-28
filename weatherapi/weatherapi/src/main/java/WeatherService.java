import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class WeatherService {

    @Inject
    @RestClient
    private OpenWeatherClient openWeatherClient;


    @ConfigProperty(name = "openweather.api.key")
    private String apiKey;

    public OpenWeatherResponse getWeather(String city, String state) {
        String location = city + "," + state;
        
        System.out.println("Requesting weather for location: " + location); 

        try {
            return openWeatherClient.getWeather(location, apiKey, "pt_br", "metric");
        } catch (Exception e) {
            System.err.println("Error fetching weather data: " + e.getMessage()); 
            throw e;
        }
    }
}