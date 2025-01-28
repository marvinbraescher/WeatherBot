
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.inject.Inject;


@QuarkusMain
public class DiscordBotApplication implements io.quarkus.runtime.QuarkusApplication {

    @Inject
    WeatherBot discordBot;

    public static void main(String... args) {
        Quarkus.run(DiscordBotApplication.class, args);
    }

    @Override
    public int run(String... args) {
        discordBot.startBot();
        Quarkus.waitForExit();
        return 0;
    }
}