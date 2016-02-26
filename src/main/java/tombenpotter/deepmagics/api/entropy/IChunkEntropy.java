package tombenpotter.deepmagics.api.entropy;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public interface IChunkEntropy {

    public boolean isHasStructure();

    public long getMaxStoreableEnergy();

    public long getMaxAccessibleEnergy();

    public long getMaxEnergyBeforeRipple();

    public long getMaxSafelyStoreableEnergy();

    public long getTotalStoredEnergy();

    public ChunkCoordIntPair getChunkCoords();

    public long getEnergy(World world);

    public List<ChunkCoordIntPair> getAdjacentChunks();

    @SideOnly(Side.SERVER)
    public void update(World world);

    public void ripple(World world);

    public void applyEffects(World world);

    public void send(World world, IChunkEntropy receiver, long energy);

    public void receive(World world, IChunkEntropy sender, long energy);

    public void readFromNBT(NBTTagCompound tagCompound);

    public void writeToNBT(NBTTagCompound tagCompound);
}
