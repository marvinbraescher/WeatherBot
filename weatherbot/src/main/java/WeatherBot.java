
import java.util.logging.Logger;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.json.JSONObject;

import discord4j.common.util.Snowflake;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import reactor.core.publisher.Mono;

@ApplicationScoped
public class WeatherBot {

    @ConfigProperty(name = "discord.token")
    String token;

    @ConfigProperty(name = "discord.channelId")
    String channelId;

    @Inject
    @RestClient
    private WeatherResource weatherResource;

    private static final Logger LOGGER = Logger.getLogger(WeatherBot.class.getName());
    private GatewayDiscordClient gateway;

    public void startBot() {
        LOGGER.info("Starting bot with token: " + token);

    DiscordClient client = DiscordClient.create(token);
    gateway = client.login().block();

    if (gateway != null) {
        gateway.on(MessageCreateEvent.class, event -> {
            Message message = event.getMessage();
            LOGGER.info("Received message: " + message);
            String messageContent = message.getContent();
            String nickname =  message.getAuthor().get().getUsername();
            createJWT(nickname);
            MessageChannel channel = message.getChannel().block();
            if ("!menu".equals(messageContent)) {
                if (channel != null) {
                    String menu = """
                        **WeatherBot Menu**
                        - `!ping` - Testa a conexão com o bot
                        - `!weather cidade, país` - Consulta a previsão do tempo
                        - Exemplo: `!weather São Paulo, BR`
                        """;
                    channel.createMessage(menu).block();
                }
            } else if (messageContent.startsWith("!weather")) {
                if (channel != null) {
                    LOGGER.info("Received message with content: '" + messageContent + "'");
                    try {
                        String[] parts = messageContent.substring("!weather".length()).trim().split(",");
                        if (parts.length == 2) {
                            String city = parts[0];
                            String state = parts[1].trim();
                            OpenWeatherResponse weatherResponse = weatherResource.getCurrentWeather(city, state);
                            StringBuilder weatherMessage = new StringBuilder();
                            weatherMessage.append("**Previsão do tempo para ").append(city).append(", ").append(state).append(":**\n");
                            weatherMessage.append("🌡️ Temperatura atual: ").append(weatherResponse.main.temp).append("°C\n");
                            weatherMessage.append("🌡️ Temperatura mínima: ").append(weatherResponse.main.tempMin).append("°C\n");
                            weatherMessage.append("🌡️ Temperatura máxima: ").append(weatherResponse.main.tempMax).append("°C\n");
                            weatherMessage.append("💧 Umidade: ").append(weatherResponse.main.humidity).append("%\n");
                            weatherMessage.append("🌥️ Descrição: ").append(weatherResponse.weather[0].description).append("\n");
                            if (weatherResponse.rain != null) {
                                weatherMessage.append("🌧️ Precipitação: ");
                                if (weatherResponse.rain.rain1h > 0) {
                                    weatherMessage.append(weatherResponse.rain.rain1h).append("mm (última hora)\n");
                                }
                                if (weatherResponse.rain.rain3h > 0) {
                                    weatherMessage.append(weatherResponse.rain.rain3h).append("mm (últimas 3 horas)\n");
                                }
                            }
                            channel.createMessage(weatherMessage.toString()).block();
                        } else {
                            channel.createMessage("Formato inválido. Use: `!weather cidade, país`").block();
                        }
                    } catch (Exception e) {
                        channel.createMessage("Cidade não encontrada utilize ISO 3166 Country Codes ").block();
                    }
                }
            } else if ("!ping".equals(messageContent)) {
                if (channel != null) {
                    channel.createMessage("Pong!").block();
                }
            }else{
            
            }

            return Mono.empty();
        }).subscribe();

        gateway.onDisconnect().block();
    } else {
        LOGGER.severe("Failed to connect to Discord.");
    }
    }

    

    public void sendMessage(String messageContent) {
        if (gateway != null) {
            MessageChannel channel = gateway.getChannelById(Snowflake.of(channelId)).ofType(MessageChannel.class)
                    .block();
            if (channel != null) {
                channel.createMessage(messageContent).block();
            }
        } else {
            LOGGER.severe("Gateway is not connected.");
        }
    }

    public static String createJWT(String nickname) {
  
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("fullName", nickname);
        return jsonObject.toString();
    }
}
