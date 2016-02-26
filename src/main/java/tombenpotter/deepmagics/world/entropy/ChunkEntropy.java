package tombenpotter.deepmagics.world.entropy;

import lombok.Getter;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import tombenpotter.deepmagics.api.Constants;
import tombenpotter.deepmagics.api.entropy.IChunkEntropy;
import tombenpotter.deepmagics.util.Utils;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ChunkEntropy implements IChunkEntropy {

    private boolean hasStructure;
    private long totalStoredEnergy;
    private long maxStoreableEnergy;
    private long maxAccessibleEnergy;
    private long maxEnergyBeforeRipple;
    private long maxSafelyStoreableEnergy;
    private ChunkCoordIntPair chunkCoords;

    public ChunkEntropy(boolean hasStructure, long totalStoredEnergy, long maxStoreableEnergy, long maxAccessibleEnergy, long maxEnergyBeforeRipple, long maxSafelyStoreableEnergy, ChunkCoordIntPair chunkCoords) {
        this.hasStructure = hasStructure;
        this.totalStoredEnergy = totalStoredEnergy;
        this.maxStoreableEnergy = maxStoreableEnergy;
        this.maxAccessibleEnergy = maxAccessibleEnergy;
        this.maxEnergyBeforeRipple = maxEnergyBeforeRipple;
        this.maxSafelyStoreableEnergy = maxSafelyStoreableEnergy;
        this.chunkCoords = chunkCoords;
    }

    public ChunkEntropy(boolean hasStructure, long maxStoreableEnergy, long maxAccessibleEnergy, long maxEnergyBeforeRipple, long maxSafelyStoreableEnergy, ChunkCoordIntPair chunkCoords) {
        this(hasStructure, 0, maxStoreableEnergy, maxAccessibleEnergy, maxEnergyBeforeRipple, maxSafelyStoreableEnergy, chunkCoords);
    }

    public ChunkEntropy(boolean hasStructure, long maxAccessibleEnergy, long maxEnergyBeforeRipple, long maxSafelyStoreableEnergy, ChunkCoordIntPair chunkCoords) {
        this(hasStructure, Long.MAX_VALUE, maxAccessibleEnergy, maxEnergyBeforeRipple, maxSafelyStoreableEnergy, chunkCoords);
    }

    //Using this constructor needs to be immediately followed by the use of readFromNBT.
    public ChunkEntropy() {
        this(false, Integer.MAX_VALUE, (long) Integer.MAX_VALUE * 2 / 3, (long) Integer.MAX_VALUE * 2, Constants.Misc.NO_CHUNK_COORD);
    }

    @Override
    public List<ChunkCoordIntPair> getAdjacentChunks() {
        ArrayList<ChunkCoordIntPair> list = new ArrayList<ChunkCoordIntPair>();

        list.add(new ChunkCoordIntPair(getChunkCoords().chunkXPos + 1, getChunkCoords().chunkZPos));
        list.add(new ChunkCoordIntPair(getChunkCoords().chunkXPos - 1, getChunkCoords().chunkZPos));
        list.add(new ChunkCoordIntPair(getChunkCoords().chunkXPos, getChunkCoords().chunkZPos + 1));
        list.add(new ChunkCoordIntPair(getChunkCoords().chunkXPos, getChunkCoords().chunkZPos - 1));

        return list;
    }

    @Override
    public long getEnergy(World world) {
        return Math.max(getTotalStoredEnergy(), getMaxAccessibleEnergy());
    }

    @Override
    public void update(World world) {
        //TODO: Do something
    }

    @Override
    public void ripple(World world) {
        //TODO: Do something
    }

    @Override
    public void applyEffects(World world) {
        //TODO: Do something
    }

    @Override
    public void send(World world, IChunkEntropy receiver, long energy) {
        //TODO: Do something
    }

    @Override
    public void receive(World world, IChunkEntropy sender, long energy) {
        //TODO: Do something
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        chunkCoords = Utils.chunkCoordIntPairFromString(tagCompound.getString(Constants.NBT.CHUNK_COORDS));
        hasStructure = tagCompound.getBoolean(Constants.NBT.HAS_STRUCTURE);
        totalStoredEnergy = tagCompound.getLong(Constants.NBT.TOTAL_STORED_ENERGY);
        maxStoreableEnergy = tagCompound.getLong(Constants.NBT.MAX_STOREABLE_ENERGY);
        maxAccessibleEnergy = tagCompound.getLong(Constants.NBT.MAX_ACCESSIBLE_ENERGY);
        maxEnergyBeforeRipple = tagCompound.getLong(Constants.NBT.MAX_ENERGY_BEFORE_RIPPLE);
        maxSafelyStoreableEnergy = tagCompound.getLong(Constants.NBT.MAX_SAFELY_STOREABLE_ENERGY);
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        tagCompound.setString(Constants.NBT.CHUNK_COORDS, getChunkCoords().toString());
        tagCompound.setBoolean(Constants.NBT.HAS_STRUCTURE, isHasStructure());
        tagCompound.setLong(Constants.NBT.TOTAL_STORED_ENERGY, getTotalStoredEnergy());
        tagCompound.setLong(Constants.NBT.MAX_STOREABLE_ENERGY, getMaxStoreableEnergy());
        tagCompound.setLong(Constants.NBT.MAX_ACCESSIBLE_ENERGY, getMaxAccessibleEnergy());
        tagCompound.setLong(Constants.NBT.MAX_ENERGY_BEFORE_RIPPLE, getMaxEnergyBeforeRipple());
        tagCompound.setLong(Constants.NBT.MAX_SAFELY_STOREABLE_ENERGY, getMaxSafelyStoreableEnergy());
    }
}
