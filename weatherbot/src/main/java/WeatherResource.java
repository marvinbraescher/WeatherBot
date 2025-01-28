
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "https://localhost:8443")
@Path("/weather")
public interface WeatherResource {

    @GET
    @Path("/current")
    @Produces(MediaType.APPLICATION_JSON)
    public OpenWeatherResponse getCurrentWeather(@QueryParam("city") String city, @QueryParam("state") String state);
}
