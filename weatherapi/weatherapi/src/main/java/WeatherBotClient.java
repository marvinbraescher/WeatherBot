import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@RegisterRestClient(baseUri = "https://localhost:8444/discord")
public interface WeatherBotClient {
    @GET
    @Path("/ping")
    public Response ping();
    
    @POST
    @Path("/send")
    public Response sendMessage(String message);
    
    @POST
    @Path("/getJwt")
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String generate(final String fullName) ;
}
