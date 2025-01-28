import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/data/2.5/weather")
@RegisterRestClient(baseUri = "https://api.openweathermap.org")
public interface OpenWeatherClient 
{
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    OpenWeatherResponse getWeather(@QueryParam("q") String location, @QueryParam("appid") String apiKey, @QueryParam("lang") String lang, @QueryParam("units") String units);
}