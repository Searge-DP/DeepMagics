package tombenpotter.deepmagics.world.entropy;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tombenpotter.deepmagics.api.Constants;
import tombenpotter.deepmagics.api.entropy.IChunkEntropy;
import tombenpotter.deepmagics.api.entropy.IWorldEntropy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UniversalEntropy extends WorldSavedData {

    private ConcurrentHashMap<Integer, IWorldEntropy> entropyPerDimension;

    public UniversalEntropy() {
        super(Constants.Misc.WORLD_DATA_ENTROPY);
        entropyPerDimension = new ConcurrentHashMap<Integer, IWorldEntropy>();
    }

    public void createWorldEntropy(int dimensionID) {
        if (!entropyPerDimension.containsKey(dimensionID)) {
            entropyPerDimension.put(dimensionID, new WorldEntropy(dimensionID));
        }
    }

    public void deleteWorldEntropy(int dimensionID) {
        entropyPerDimension.remove(dimensionID);
    }

    public IWorldEntropy getWorldEntropy(int dimensionID) {
        return entropyPerDimension.get(dimensionID);
    }

    public IChunkEntropy getChunkEntropy(int dimensionID, ChunkCoordIntPair chunkCoordIntPair) {
        if (entropyPerDimension.containsKey(dimensionID)) {
            return getWorldEntropy(dimensionID).getChunkEntropy(chunkCoordIntPair);
        }
        return null;
    }

    public IChunkEntropy getChunkEntropy(int dimensionID, int worldX, int worldZ) {
        return getChunkEntropy(dimensionID, new ChunkCoordIntPair(worldX >> 4, worldZ >> 4));
    }

    public IChunkEntropy getChunkEntropy(Chunk chunk) {
        return getChunkEntropy(chunk.getWorld().provider.getDimensionId(), chunk.getChunkCoordIntPair());
    }

    @SideOnly(Side.SERVER)
    public void update(World world) {
        entropyPerDimension.get(world.provider.getDimensionId()).update(world);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        NBTTagList tagList = tagCompound.getTagList(Constants.Misc.WORLD_DATA_ENTROPY, net.minecraftforge.common.util.Constants.NBT.TAG_COMPOUND);
        if (tagList != null) {
            int dimensionID;
            for (int i = 0; i < tagList.tagCount(); i++) {
                NBTTagCompound tag = tagList.getCompoundTagAt(i);
                dimensionID = tag.getInteger(Constants.NBT.DIMENSION_ID);
                if (entropyPerDimension.containsKey(dimensionID)) {
                    entropyPerDimension.get(dimensionID).readFromNBT(tag);
                } else {
                    IWorldEntropy worldEntropy = new WorldEntropy();
                    worldEntropy.readFromNBT(tag);
                    entropyPerDimension.put(dimensionID, worldEntropy);
                }
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        if (!entropyPerDimension.isEmpty()) {
            NBTTagList tagList = new NBTTagList();
            for (Map.Entry<Integer, IWorldEntropy> entry : entropyPerDimension.entrySet()) {
                NBTTagCompound tag = new NBTTagCompound();
                tag.setInteger(Constants.NBT.DIMENSION_ID, entry.getKey());
                entry.getValue().writeToNBT(tag);
                tagList.appendTag(tag);
            }
            tagCompound.setTag(Constants.Misc.WORLD_DATA_ENTROPY, tagList);
        }
    }
}
