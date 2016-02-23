package tombenpotter.deepmagics.api.world;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import tombenpotter.deepmagics.api.DMBlockPos;

public interface IStructure {

    public void generateStructure(World world, int chunkX, int chunkZ, IChunkProvider chunkGenerator, IChunkProvider chunkProvider);

    public DMBlockPos getStructurePos();

    public void writeToNBT(NBTTagCompound tagCompound);
}
