package confusedalex.simpleuhc;

import static confusedalex.simpleuhc.SimpleUHC.instance;

public class Config {
    public static void loadConfig() {
        instance.getConfig().options().header("Countdown: Time in seconds \n" +
                "ProtectionTime: Time in minutes and not under 1 \n" +
                "FriendFire: Determines whether FriendlyFire should be enabled or not \n" +
                "InitialBorderSize: Initial size of the border \n");

        instance.getConfig().addDefault("Countdown", 30);
        instance.getConfig().addDefault("ProtectionTime", 5);
        instance.getConfig().addDefault("FriendlyFire", false);
        instance.getConfig().addDefault("InitialBorderSize", 600);

        instance.getConfig().options().copyDefaults(true);
        instance.saveConfig();
    }
}
