package tombenpotter.deepmagics.registry;

import lombok.Getter;
import net.minecraftforge.fml.common.registry.GameRegistry;
import tombenpotter.deepmagics.util.IRegistry;
import tombenpotter.deepmagics.world.structure.StructureGenerator;

public class ModStructures implements IRegistry {

    @Getter
    private static ModStructures instance = new ModStructures();

    @Getter
    private StructureGenerator structureGenerator;

    private ModStructures() {
        structureGenerator = new StructureGenerator();
    }

    @Override
    public void init() {
        registerStructures();
        registerStructureGenerator();
    }

    private void registerStructures() {

    }

    private void registerStructureGenerator() {
        GameRegistry.registerWorldGenerator(ModStructures.getInstance().getStructureGenerator(), 0);
    }
}
