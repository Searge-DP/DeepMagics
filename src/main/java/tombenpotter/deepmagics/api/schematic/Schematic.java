package tombenpotter.deepmagics.api.schematic;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.BlockPos;
import tombenpotter.deepmagics.api.world.Area;

import java.util.HashMap;
import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
public abstract class Schematic implements ISchematic {

    //This class was originally made by @Lumaceon. I just took it, changed it around a bit and updated it.

    private final NBTTagList tileDataList;
    private short width, height, length, horizon; //Horizon sets the "ground" or how far down to translate this.
    private byte[] data;
    private HashMap<BlockPos, NBTTagCompound> tiles;
    private int areaBlockCount; //Does not actually count blocks, just the size of the area in blocks.

    public Schematic(NBTTagList tileEntities, short width, short height, short length, short horizon, byte[] data) {
        this.tileDataList = tileEntities;
        this.width = width;
        this.height = height;
        this.length = length;
        this.horizon = horizon;
        this.data = data;
        areaBlockCount = width * height * length;
        tiles = Maps.newHashMap();
        reloadTileMap();
    }

    @Override
    public NBTTagCompound getTileData(BlockPos schematicPos, BlockPos worldPos) {
        return getTileData(schematicPos.getX(), schematicPos.getY(), schematicPos.getZ(), worldPos.getX(), worldPos.getY(), worldPos.getZ());
    }

    @Override
    public NBTTagCompound getTileData(int schematicX, int schematicY, int schematicZ, int worldX, int worldY, int worldZ) {
        NBTTagCompound tag = getTileData(schematicX, schematicY, schematicZ);
        if (tag != null) {
            tag.setInteger("x", worldX);
            tag.setInteger("y", worldY);
            tag.setInteger("z", worldZ);
            return tag;
        }
        return null;
    }

    @Override
    public NBTTagCompound getTileData(BlockPos blockPos) {
        NBTTagCompound tag = tiles.get(blockPos);
        if (tag != null) {
            return (NBTTagCompound) tag.copy();
        }
        return null;
    }

    @Override
    public NBTTagCompound getTileData(int x, int y, int z) {
        return getTileData(new BlockPos(x, y, z));
    }

    @Override
    public byte getMetadata(BlockPos blockPos) {
        return getMetadata(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    @Override
    public byte getMetadata(int x, int y, int z) {
        return data[getIndexFromCoordinates(x, y, z)];
    }

    public Area getAreaFromWorldCoordinates(BlockPos blockPos) {
        return getAreaFromWorldCoordinates(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    public Area getAreaFromWorldCoordinates(int x, int y, int z) {
        int minX, maxX, minY, maxY, minZ, maxZ;
        if (width % 2 == 0) {
            minX = x + 1 - width / 2;
            maxX = x + width / 2;
        } else {
            minX = x - (width - 1) / 2;
            maxX = x + (width - 1) / 2;
        }

        minY = y - horizon;
        maxY = (y - horizon) + height;

        if (length % 2 == 0) {
            minZ = z + 1 - length / 2;
            maxZ = z + length / 2;
        } else {
            minZ = z - (length - 1) / 2;
            maxZ = z + (length - 1) / 2;
        }

        return new Area(minX, minY, minZ, maxX, maxY, maxZ);
    }

    public int[] getSchematicToWorldOffset(BlockPos blockPos) {
        return getSchematicToWorldOffset(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    public int[] getSchematicToWorldOffset(int worldX, int worldY, int worldZ) {
        int[] result = new int[3];

        if (width % 2 == 0) {
            result[0] = (-worldX + width / 2) + 1;
        } else {
            result[0] = -worldX + width / 2;
        }

        result[1] = -worldY + horizon;

        if (length % 2 == 0) {
            result[2] = (-worldZ + length / 2) + 1;
        } else {
            result[2] = -worldZ + length / 2;
        }

        return result;
    }

    protected int getIndexFromCoordinates(int x, int y, int z) {
        return y * width * length + z * width + x;
    }

    protected void reloadTileMap() {
        tiles.clear();
        for (int i = 0; i < tileDataList.tagCount(); i++) {
            NBTTagCompound tag = (NBTTagCompound) tileDataList.getCompoundTagAt(i).copy();
            BlockPos pos = new BlockPos(tag.getInteger("x"), tag.getInteger("y"), tag.getInteger("z"));
            tiles.put(pos, tag);
        }
    }

    @Getter
    @ToString
    @EqualsAndHashCode(callSuper = true)
    public static class StandardSchematic extends Schematic {
        private byte[] blocks;

        public StandardSchematic(NBTTagList tileEntities, short width, short height, short length, short horizon, byte[] blocks, byte[] data) {
            super(tileEntities, width, height, length, horizon, data);
            this.blocks = blocks;
        }

        @Override
        public Block getBlock(BlockPos blockPos) {
            return getBlock(blockPos.getX(), blockPos.getY(), blockPos.getZ());
        }

        @Override
        public Block getBlock(int x, int y, int z) {
            if (x < getWidth() && y < getHeight() && z < getLength()) {
                return Block.getBlockById(blocks[getIndexFromCoordinates(x, y, z)]);
            }
            return null;
        }
    }

    @Getter
    @ToString
    @EqualsAndHashCode(callSuper = true)
    public static class ModSchematic extends Schematic implements IModSchematic {

        public int[] blocks;
        private List<String> missingBlocks; //An array of unlocalized names which are missing in this MC instance.

        public ModSchematic(NBTTagList tileEntities, short width, short height, short length, short horizon, int[] blocks, byte[] data, String[] missingBlocks) {
            super(tileEntities, width, height, length, horizon, data);
            this.blocks = blocks;
            this.missingBlocks = Lists.newArrayList(missingBlocks);
        }

        @Override
        public Block getBlock(BlockPos blockPos) {
            return getBlock(blockPos.getX(), blockPos.getY(), blockPos.getZ());
        }

        @Override
        public Block getBlock(int x, int y, int z) {
            if (x < getWidth() && y < getHeight() && z < getLength()) {
                return Block.getBlockById(blocks[getIndexFromCoordinates(x, y, z)]);
            }
            return null;
        }

        @Override
        public List<String> getMissingBlocks() {
            return ImmutableList.copyOf(missingBlocks);
        }
    }
}
