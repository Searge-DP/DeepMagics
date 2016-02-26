package tombenpotter.deepmagics.api.entropy;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IWorldEntropy {

    public int getDimensionID();

    public IChunkEntropy getChunkEntropy(ChunkCoordIntPair chunkCoordIntPair);

    public IChunkEntropy getChunkEntropy(int worldX, int worldZ);

    public IChunkEntropy getChunkEntropy(Chunk chunk);

    public void addChunkEntropy(ChunkCoordIntPair chunkCoordIntPair, IChunkEntropy chunkEntropy);

    public void addChunkEntropy(int worldX, int worldY, IChunkEntropy chunkEntropy);

    public void addChunkEntropy(IChunkEntropy chunkEntropy);

    public void setChunkEntropy(ChunkCoordIntPair chunkCoordIntPair, IChunkEntropy chunkEntropy);

    public void setChunkEntropy(int worldX, int worldZ, IChunkEntropy chunkEntropy);

    public void setChunkEntropy(IChunkEntropy chunkEntropy);

    @SideOnly(Side.SERVER)
    public void update(World world);

    public void readFromNBT(NBTTagCompound tagCompound);

    public void writeToNBT(NBTTagCompound tagCompound);
};
