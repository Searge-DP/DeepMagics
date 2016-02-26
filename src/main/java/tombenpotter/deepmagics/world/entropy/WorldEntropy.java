package tombenpotter.deepmagics.world.entropy;

import lombok.Getter;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import tombenpotter.deepmagics.api.Constants;
import tombenpotter.deepmagics.api.entropy.IChunkEntropy;
import tombenpotter.deepmagics.api.entropy.IWorldEntropy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WorldEntropy implements IWorldEntropy {
    @Getter
    private int dimensionID;

    private ConcurrentHashMap<ChunkCoordIntPair, IChunkEntropy> map;

    public WorldEntropy(int dimensionID) {
        this.dimensionID = dimensionID;
        map = new ConcurrentHashMap<ChunkCoordIntPair, IChunkEntropy>();
    }

    public WorldEntropy() {
        this(0);
    }

    @Override
    public IChunkEntropy getChunkEntropy(ChunkCoordIntPair chunkCoordIntPair) {
        return map.get(chunkCoordIntPair);
    }

    @Override
    public IChunkEntropy getChunkEntropy(int worldX, int worldZ) {
        return getChunkEntropy(new ChunkCoordIntPair(worldX >> 4, worldZ >> 4));
    }

    @Override
    public IChunkEntropy getChunkEntropy(Chunk chunk) {
        return getChunkEntropy(chunk.getChunkCoordIntPair());
    }

    @Override
    public void addChunkEntropy(ChunkCoordIntPair chunkCoordIntPair, IChunkEntropy chunkEntropy) {
        map.put(chunkCoordIntPair, chunkEntropy);
    }

    @Override
    public void addChunkEntropy(int worldX, int worldY, IChunkEntropy chunkEntropy) {
        addChunkEntropy(new ChunkCoordIntPair(worldX >> 4, worldY >> 4), chunkEntropy);
    }

    @Override
    public void addChunkEntropy(IChunkEntropy chunkEntropy) {
        addChunkEntropy(chunkEntropy.getChunkCoords(), chunkEntropy);
    }

    @Override
    public void setChunkEntropy(ChunkCoordIntPair chunkCoordIntPair, IChunkEntropy chunkEntropy) {
        map.put(chunkCoordIntPair, chunkEntropy);
    }

    @Override
    public void setChunkEntropy(int worldX, int worldZ, IChunkEntropy chunkEntropy) {
        setChunkEntropy(new ChunkCoordIntPair(worldX >> 4, worldZ >> 4), chunkEntropy);
    }

    @Override
    public void setChunkEntropy(IChunkEntropy chunkEntropy) {
        setChunkEntropy(chunkEntropy.getChunkCoords(), chunkEntropy);
    }

    @Override
    public void update(World world) {
        for (Map.Entry<ChunkCoordIntPair, IChunkEntropy> entry : map.entrySet()) {
            entry.getValue().update(world);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        NBTTagList tagList = tagCompound.getTagList(Constants.NBT.ENTROPY_PER_CHUNK, net.minecraftforge.common.util.Constants.NBT.TAG_COMPOUND);
        if (tagList != null) {
            for (int i = 0; i < tagList.tagCount(); i++) {
                IChunkEntropy chunkEntropy = new ChunkEntropy();
                chunkEntropy.readFromNBT(tagList.getCompoundTagAt(i));
                setChunkEntropy(chunkEntropy);
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        if (!map.isEmpty()) {
            NBTTagList tagList = new NBTTagList();
            for (IChunkEntropy chunkEntropy : map.values()) {
                NBTTagCompound tag = new NBTTagCompound();
                chunkEntropy.writeToNBT(tag);
                tagList.appendTag(tagCompound);
            }
            tagCompound.setTag(Constants.NBT.ENTROPY_PER_CHUNK, tagCompound);
        }
    }
}
