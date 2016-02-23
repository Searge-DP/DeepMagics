package tombenpotter.deepmagics.api;

import lombok.Getter;
import lombok.Setter;
import tombenpotter.deepmagics.api.util.LogHelper;

import java.io.File;

public class DeepMagicsAPI {

    @Getter
    private static LogHelper logger = new LogHelper(Constants.Mod.API);

    @Getter
    @Setter
    private static File configurationDirectory;     //Do not use before Pre-Init!
}
