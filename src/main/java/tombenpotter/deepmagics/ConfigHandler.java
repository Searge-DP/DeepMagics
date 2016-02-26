package tombenpotter.deepmagics;

import lombok.Getter;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tombenpotter.deepmagics.api.Constants;
import tombenpotter.deepmagics.repack.tehnut.lib.annot.Handler;

import java.io.File;

@Handler
public class ConfigHandler {

    @Getter
    private static Configuration config;

    public static void init(File file) {
        config = new Configuration(file);
        syncConfig();
    }

    public static void syncConfig() {

        if (config.hasChanged()) {
            config.save();
        }
    }

    @SubscribeEvent
    public void configChanged(ConfigChangedEvent event) {
        if (event.modID.equals(Constants.Mod.MODID)) {
            syncConfig();
        }
    }
}
