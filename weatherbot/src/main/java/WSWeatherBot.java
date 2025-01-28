import java.util.Arrays;
import java.util.HashSet;

import org.eclipse.microprofile.jwt.Claims;

import io.smallrye.jwt.build.Jwt;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/discord")
public class WSWeatherBot {

    @Inject
    WeatherBot weatherBot;

    private static final String ISSUER = "http://localhost:8080";

    @GET
    @Path("/ping")
    public Response ping() {
        // Chama o método do bot para enviar uma mensagem de ping
        weatherBot.sendMessage("!ping");
        return Response.ok("Funcionando").build();
    }


    @POST
    @Path("/send")
    public Response sendMessage(String message) 
    {
        // Envia uma mensagem para o canal do bot
        weatherBot.sendMessage(message);
        
        return Response.ok("Message sent to bot").build();
    }

    @POST
    @Path("/getJwt")
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String generate(final String fullName) {
        return Jwt.issuer(ISSUER)
                .upn("TESTE@gmail.com")
                .groups(new HashSet<>(Arrays.asList("User", "Admin")))
                .claim(Claims.full_name, fullName)
                .sign();
    }
}
