

import org.eclipse.microprofile.rest.client.inject.RestClient;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/weather")
public class WeatherResource {

    @Inject
    WeatherService weatherService;

    
    @Inject
    @RestClient
    private WeatherBotClient weatherBotClient;



    @GET
    @Path("/current")
    @Produces(MediaType.APPLICATION_JSON)
    public OpenWeatherResponse getCurrentWeather(@QueryParam("city") String city, @QueryParam("state") String state) {

        System.out.println("Received request for city: " + city + ", state: " + state);

        try {
            // Obtendo a resposta do serviço de clima
            OpenWeatherResponse weatherResponse = weatherService.getWeather(city, state);

            return weatherResponse;
        } catch (Exception e) {
            System.err.println("Error handling weather request: " + e.getMessage());

            throw new RuntimeException("Error fetching weather data", e);
        }
    }
}