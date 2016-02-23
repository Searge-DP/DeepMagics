package tombenpotter.deepmagics.world;

import lombok.Getter;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import tombenpotter.deepmagics.api.BlockStack;
import tombenpotter.deepmagics.api.Constants;
import tombenpotter.deepmagics.api.world.EnumGenerationType;
import tombenpotter.deepmagics.api.world.IStructure;
import tombenpotter.deepmagics.api.DMBlockPos;
import tombenpotter.deepmagics.util.Utils;
import tombenpotter.deepmagics.api.world.Area;
import tombenpotter.deepmagics.api.schematic.Schematic;

@Getter
public class Structure implements IStructure {

    private DMBlockPos structurePos;
    private StructureTemplate structureTemplate;
    private EnumGenerationType enumGenerationType;

    public Structure(DMBlockPos structurePos, StructureTemplate structureTemplate) {
        this.structurePos = structurePos;
        this.structureTemplate = structureTemplate;
        this.enumGenerationType = EnumGenerationType.NONE;
    }

    public Structure(int xCoord, int zCoord, StructureTemplate structureTemplate) {
        this.structurePos = new DMBlockPos(xCoord, 0, zCoord);
        this.enumGenerationType = structureTemplate.getEnumGenerationType();
    }

    @Override
    public void generateStructure(World world, int chunkX, int chunkZ, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        if (getEnumGenerationType() != EnumGenerationType.NONE) {
            this.structurePos = getEnumGenerationType().getStructurePos(world, structurePos);
        }

        Schematic schematic = structureTemplate.getSchematic();
        Area schematicArea = schematic.getAreaFromWorldCoordinates(structurePos);

        if (schematicArea.doAreasIntersect(new Area(chunkX << 4, 0, chunkZ << 4, (chunkX << 4) + 16, 255, (chunkZ << 4) + 16))) {
            BlockStack blockStack;
            NBTTagCompound tileData;
            DMBlockPos tempPos = new DMBlockPos();

            for (int x = 0; x < schematic.getWidth(); x++) {
                for (int y = 0; y < schematic.getHeight(); y++) {
                    for (int z = 0; z < schematic.getLength(); z++) {

                        blockStack = new BlockStack(schematic.getBlock(x, y, z), schematic.getMetadata(x, y, z));
                        tempPos.set(structurePos.getX() + x, structurePos.getY() + y, structurePos.getZ() + z);

                        if (blockStack.getBlock() != null) {
                            world.setBlockState(tempPos, Blocks.air.getDefaultState(), 2);

                            if (!blockStack.getBlock().getMaterial().equals(Material.air)) {
                                world.setBlockState(tempPos, blockStack.getState(), 2);

                                if (blockStack.getBlock().hasTileEntity(blockStack.getState())) {
                                    tileData = schematic.getTileData(new BlockPos(x, y, z), tempPos);

                                    if (tileData != null) {
                                        world.getTileEntity(tempPos).readFromNBT(tileData);
                                        world.markBlockForUpdate(tempPos);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            for (int i = chunkX; i < chunkX + (schematicArea.getWidth() >> 4); i++) {
                for (int j = chunkZ; j < chunkZ + (schematicArea.getLength() >> 4); j++) {
                    Chunk chunk = world.getChunkFromChunkCoords(chunkX, chunkZ);
                    chunk.setChunkModified();
                    chunk.enqueueRelightChecks();
                }
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        tagCompound = Utils.checkNBT(tagCompound);
        structurePos.writeToNBT(tagCompound);
        tagCompound.setString(Constants.NBT.STRUCTURE_FILE_NAME, structureTemplate.getFileName());
        tagCompound.setString(Constants.NBT.ENUM_GENERATION_TYPE, enumGenerationType.name());
    }
}
