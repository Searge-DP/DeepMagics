package tombenpotter.deepmagics.util.helper;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import tombenpotter.deepmagics.DeepMagics;
import tombenpotter.deepmagics.api.Constants;
import tombenpotter.deepmagics.api.DeepMagicsAPI;
import tombenpotter.deepmagics.world.schematic.Schematic;
import tombenpotter.deepmagics.util.Utils;
import tombenpotter.deepmagics.api.world.Area;

import java.io.*;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SchematicHelper {

    //This class was originally made by @Lumaceon. I just took it, changed it around a bit and updated it.

    public static final SchematicHelper INSTANCE = new SchematicHelper();

    private SchematicHelper() {
    }

    /**
     * Loads a .modschematic file and returns a ModSchematic class. Does not load .schematic files.
     *
     * @param fileName        The name of the file to load (file extension should not be included).
     * @param defaultResource If true, checks the schematics folder under cmo resources.
     *                        If false, checks modschematics folder (which is a sub of the main MC Directory).
     * @return The loaded ModSchematic.
     */
    public Schematic.ModSchematic loadModSchematic(String fileName, boolean defaultResource) {
        try {
            InputStream is;
            if (defaultResource) {
                is = Utils.getFromResource(new ResourceLocation(Constants.Mod.MODID.toLowerCase(), "schematics/" + fileName + ".modschematic"));
            } else {
                is = new FileInputStream(new File(DeepMagics.getConfigDirectory(), "/modschematics/" + fileName + ".modschematic"));
            }
            return loadModSchematic(is, fileName);
        } catch (Exception e) {
            DeepMagicsAPI.getLogger().severe("Error loading modschematic file: \"" + fileName + "\"");
            DeepMagicsAPI.getLogger().severe(e.getLocalizedMessage());
            return null;
        }
    }

    /**
     * Loads a .modschematic file and returns a ModSchematic class. Does not load .schematic files.
     *
     * @param is            The InputStream of the file to load.
     * @param schematicName Used for logging information about the structure, nothing else.
     * @return The loaded ModSchematic.
     */
    public Schematic.ModSchematic loadModSchematic(InputStream is, String schematicName) {
        try {
            NBTTagCompound nbt = CompressedStreamTools.readCompressed(is);
            is.close();
            NBTTagList tileEntities = nbt.getTagList("TileEntities", 10);
            short width = nbt.getShort("Width");
            short height = nbt.getShort("Height");
            short length = nbt.getShort("Length");
            short horizon = nbt.getShort("Horizon");
            int[] blocks = nbt.getIntArray("Blocks");
            byte[] metadata = nbt.getByteArray("Data");

            //----------\\____ID Mapping____//----------\\
            NBTTagList idMapping = nbt.getTagList("IDMap", 10);
            ConcurrentHashMap<Integer, Integer> idMatrix = new ConcurrentHashMap<Integer, Integer>(idMapping.tagCount());
            ArrayList<String> missingBlocks = new ArrayList<String>();

            NBTTagCompound tempTag;
            int id;
            String modId;
            String name;
            Block targetBlock;
            for (int n = 0; n < idMapping.tagCount(); n++) {
                tempTag = idMapping.getCompoundTagAt(n);
                id = tempTag.getInteger("ID");
                modId = tempTag.getString("UI_Mod");
                name = tempTag.getString("UI_Name");

                targetBlock = GameRegistry.findBlock(modId, name);
                DeepMagicsAPI.getLogger().info(modId + ":" + name);
                if (targetBlock == null) //Block no longer exists.
                {
                    missingBlocks.add(modId + ":" + name);
                    idMatrix.put(id, Block.getIdFromBlock(Blocks.air)); //Change to air.
                } else if (!targetBlock.equals(Block.getBlockById(id))) { //Id mapping for this block has changed.
                    idMatrix.put(id, Block.getIdFromBlock(targetBlock));
                }
                //else: Block still exists and is the same block it was when this schematic was created; do nothing.
            }

            this.convertBlocksWithIdMatrix(blocks, idMatrix);
            //----------\\____ID Mapping____//----------\\

            DeepMagicsAPI.getLogger().info("/-ModSchematic \"" + schematicName + "\" loaded-\\");
            DeepMagicsAPI.getLogger().info("Width: " + width + ", Height: " + height + ", Length: " + length + ", Tile Entity Count: " + tileEntities.tagCount());

            Set<Integer> set = idMatrix.keySet();
            for (int i : set) {
                DeepMagicsAPI.getLogger().info("Old: " + i + ", New: " + idMatrix.get(i));
            }
            return new Schematic.ModSchematic(tileEntities, width, height, length, horizon, blocks, metadata, missingBlocks.toArray(new String[missingBlocks.size()]));
        } catch (Exception e) {
            DeepMagicsAPI.getLogger().severe("Error loading modschematic file: \"" + schematicName + "\"");
            DeepMagicsAPI.getLogger().severe(e.getLocalizedMessage());
            return null;
        }
    }

    /**
     * Loads a .schematic file and returns a ModSchematic class. Does not load .modschematic files.
     *
     * @param fileName        The name of the file to load (file extension should not be included).
     * @param defaultResource If true, checks the schematics folder under cmo resources.
     *                        If false, checks modschematics folder (which is a sub of the main MC Directory).
     * @return The loaded Schematic.
     */
    public Schematic.StandardSchematic loadSchematic(String fileName, boolean defaultResource) {
        try {
            InputStream is;
            if (defaultResource)
                is = Utils.getFromResource(new ResourceLocation(Constants.Mod.MODID.toLowerCase(), "schematics/" + fileName + ".schematic"));
            else
                is = new FileInputStream(new File(DeepMagics.getConfigDirectory(), "/modschematics/" + fileName + ".schematic"));
            NBTTagCompound nbt = CompressedStreamTools.readCompressed(is);
            is.close();
            NBTTagList tileEntities = nbt.getTagList("TileEntities", 10);
            short width = nbt.getShort("Width");
            short height = nbt.getShort("Height");
            short length = nbt.getShort("Length");
            byte[] blocks = nbt.getByteArray("Blocks");
            byte[] metadata = nbt.getByteArray("Data");

            DeepMagicsAPI.getLogger().info("/-Schematic \"" + fileName + "\" loaded-\\");
            DeepMagicsAPI.getLogger().info("Width: " + width + ", Height: " + height + ", Length: " + length + "Tile Entity Count: " + tileEntities.tagCount());
            return new Schematic.StandardSchematic(tileEntities, width, height, length, (short) 0, blocks, metadata);
        } catch (Exception e) {
            DeepMagicsAPI.getLogger().severe("Error loading schematic file: \"" + fileName + "\"");
            DeepMagicsAPI.getLogger().severe(e.getLocalizedMessage());
            return null;
        }
    }

    /**
     * Creates a .modschematic file in the Mod Schematics folder (a sub-directory of the Minecraft directory).
     *
     * @param area     The area in theWorld which will be saved as a .modschematic file.
     * @param fileName The name of the file to create, which is automatically given the .modschematic file extension.
     */
    public void createModSchematic(World world, Area area, short horizon, String fileName) {
        if (world == null || area == null || fileName == null)
            return;

        TileEntity tileEntity;
        Block block;
        try {
            File newFile = new File(DeepMagics.getConfigDirectory(), "/modschematics");
            newFile.mkdirs();
            newFile = new File(newFile, fileName + ".modschematic");
            FileOutputStream output = new FileOutputStream(newFile);
            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setShort("Width", area.getWidth());
            nbt.setShort("Height", area.getHeight());
            nbt.setShort("Length", area.getLength());
            nbt.setShort("Horizon", horizon);
            int[] blocks = new int[area.getBlockCount()];
            byte[] data = new byte[area.getBlockCount()];
            NBTTagList tileList = new NBTTagList();
            NBTTagList idMapping = new NBTTagList();
            ConcurrentHashMap<Integer, GameRegistry.UniqueIdentifier> blockIDMap = new ConcurrentHashMap<Integer, GameRegistry.UniqueIdentifier>(200);

            int i = 0;
            int schematicX = 0;
            int schematicY = 0;
            int schematicZ = 0;
            for (int y = Math.min(area.getStartY(), area.getEndY()); y <= Math.max(area.getStartY(), area.getEndY()); y++) {
                schematicZ = 0;
                for (int z = Math.min(area.getStartZ(), area.getEndZ()); z <= Math.max(area.getStartZ(), area.getEndZ()); z++) {
                    schematicX = 0;
                    for (int x = Math.min(area.getStartX(), area.getEndX()); x <= Math.max(area.getStartX(), area.getEndX()); x++) {
                        BlockPos blockPos = new BlockPos(x, y, z);
                        block = world.getBlockState(blockPos).getBlock();
                        blocks[i] = Block.getIdFromBlock(block);

                        //If we haven't encountered this block ID yet, add its unique identifier to the ID map.
                        if (!blockIDMap.containsKey(blocks[i])) {
                            blockIDMap.put(blocks[i], GameRegistry.findUniqueIdentifierFor(block));
                        }

                        data[i] = (byte) block.getMetaFromState(world.getBlockState(blockPos));
                        tileEntity = world.getTileEntity(blockPos);
                        if (tileEntity != null) {
                            NBTTagCompound tileNBT = new NBTTagCompound();
                            tileEntity.writeToNBT(tileNBT);
                            tileNBT.setInteger("x", schematicX);
                            tileNBT.setInteger("y", schematicY);
                            tileNBT.setInteger("z", schematicZ);
                            tileList.appendTag(tileNBT);
                        }
                        ++i;
                        schematicX++;
                    }
                    schematicZ++;
                }
                schematicY++;
            }

            //For each block ID we encountered, add it to the ID mapping.
            Set<Integer> idSet = blockIDMap.keySet();
            NBTTagCompound temp;
            for (int id : idSet) {
                temp = new NBTTagCompound();
                temp.setInteger("ID", id);
                temp.setString("UI_Mod", blockIDMap.get(id).modId);
                temp.setString("UI_Name", blockIDMap.get(id).name);
                idMapping.appendTag(temp);
            }

            nbt.setIntArray("Blocks", blocks);
            nbt.setByteArray("Data", data);
            nbt.setTag("TileEntities", tileList);
            nbt.setTag("IDMap", idMapping);
            CompressedStreamTools.writeCompressed(nbt, output);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Converts an array of saved block IDs to the new ID representing that block.
     *
     * @param blocks Array of int IDs representing blocks as they were when saved.
     * @param matrix A Map class where the key is the saved ID and the value is the new ID for that block.
     */
    public void convertBlocksWithIdMatrix(int[] blocks, ConcurrentHashMap<Integer, Integer> matrix) {
        for (int n = 0; n < blocks.length; n++) {
            if (matrix.containsKey(blocks[n])) {
                blocks[n] = matrix.get(blocks[n]);
            }
        }
    }
}
