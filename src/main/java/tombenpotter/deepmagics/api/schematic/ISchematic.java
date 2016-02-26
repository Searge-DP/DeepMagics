package tombenpotter.deepmagics.api.schematic;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import tombenpotter.deepmagics.api.world.Area;

import java.util.HashMap;
import java.util.List;

public interface ISchematic {

    public short getWidth();
    public short getHeight();
    public short getLength();
    public short getHorizon();
    public byte[] getData();
    public HashMap<BlockPos, NBTTagCompound> getTiles();
    public int getAreaBlockCount();

    /**
     * Gets the block at the specified coordinates, ranging from 0 to the width (x), height (y) and length (z).
     * 0 is inclusive, the width, height and length values are exclusive.
     *
     * @param blockPos BlockPos within the local schematic coordinates.
     * @return The blocks located at the specified local schematic coordinates, or null if out of bounds.
     */
    public Block getBlock(BlockPos blockPos);

    public Block getBlock(int x, int y, int z);

    /**
     * Gets the data for the tile entity at the specified coordinates, ranging from 0 to the width (x), height (y) and length (z).
     * 0 is inclusive, the width, height and length values are exclusive.
     *
     * @param schematicPos BlockPos within the local schematic coordinates.
     * @param worldPos     BlocPos within the world coordinates
     * @return The data from the tile entity located at the specified local schematic coordinates, or null if not found.
     */
    public NBTTagCompound getTileData(BlockPos schematicPos, BlockPos worldPos);

    public NBTTagCompound getTileData(int schematicX, int schematicY, int schematicZ, int worldX, int worldY, int worldZ);

    /**
     * Gets the data for the tile entity at the specified coordinates, ranging from 0 to the width (x), height (y) and length (z).
     * 0 is inclusive, the width, height and length values are exclusive.
     *
     * @param blockPos BlockPos within local schematic coordinates.
     * @return The data from the tile entity located at the specified local schematic coordinates, or null if not found.
     */
    public NBTTagCompound getTileData(BlockPos blockPos);

    public NBTTagCompound getTileData(int x, int y, int z);

    /**
     * Gets the metadata for the block at the specified coordinates, ranging from 0 to the width (x), height (y) and
     * length (z). 0 is inclusive, the width, height and length values are exclusive.
     *
     * @param blockPos BlockPos within local schematic coordinates.
     * @return The metadata for the block at the specified local schematic coordinates.
     */
    public byte getMetadata(BlockPos blockPos);

    public byte getMetadata(int x, int y, int z);

    public Area getAreaFromWorldCoordinates(int x, int y, int z);

    public Area getAreaFromWorldCoordinates(BlockPos blockPos);

    public int[] getSchematicToWorldOffset(BlockPos blockPos);

    public int[] getSchematicToWorldOffset(int worldX, int worldY, int worldZ);

    public interface IModSchematic extends ISchematic {
        /**
         * Gets the blocks that are missing in the MC instance for this structure.
         *
         * @return A list of the missing blocks
         */
        public List<String> getMissingBlocks();

    }
}