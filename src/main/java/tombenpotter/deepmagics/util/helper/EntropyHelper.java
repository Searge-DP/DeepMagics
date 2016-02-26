package tombenpotter.deepmagics.util.helper;

import lombok.Getter;
import net.minecraft.server.MinecraftServer;
import tombenpotter.deepmagics.api.Constants;
import tombenpotter.deepmagics.world.entropy.UniversalEntropy;

public class EntropyHelper {

    @Getter
    private static EntropyHelper instance = new EntropyHelper();

    private EntropyHelper() {
    }

    public UniversalEntropy getUniversalEntropy() {
        if (MinecraftServer.getServer() == null) {
            return null;
        }
        return (UniversalEntropy) MinecraftServer.getServer().worldServers[0].loadItemData(UniversalEntropy.class, Constants.Misc.WORLD_DATA_ENTROPY);
    }
}
