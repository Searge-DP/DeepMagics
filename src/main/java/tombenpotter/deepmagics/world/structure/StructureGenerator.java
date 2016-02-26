package tombenpotter.deepmagics.world.structure;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;
import tombenpotter.deepmagics.api.structure.IStructureTemplate;
import tombenpotter.deepmagics.api.structure.StructureRegistry;

import java.util.Random;

public class StructureGenerator implements IWorldGenerator {

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        if (!world.isRemote) {
            //TODO The 50 in the random needs to be tweaked. This is just too often.
            if (!StructureRegistry.instance.isRegistryEmpty() && random.nextInt(50) == 0) {
                int x, z;
                IStructureTemplate template = StructureRegistry.instance.getStructure(random.nextInt(StructureRegistry.instance.getRegistrySize()));
                if (template.canSpawnInDimension(world.provider.getDimensionId())) {
                    x = (chunkX << 4) + random.nextInt(16);
                    z = (chunkZ << 4) + random.nextInt(16);
                    Structure structure = new Structure(x, z, template);
                    structure.generateStructure(world, chunkX, chunkZ, chunkGenerator, chunkProvider);
                }
            }
        }
    }
}
