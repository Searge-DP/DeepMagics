package tombenpotter.deepmagics.registry;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.registry.GameRegistry;
import tombenpotter.deepmagics.DeepMagics;
import tombenpotter.deepmagics.api.Constants;
import tombenpotter.deepmagics.repack.tehnut.lib.annot.ModBlock;

import java.util.HashMap;
import java.util.Map;

public class ModBlocks {

    private static Map<Class<? extends Block>, String> classToName = new HashMap<Class<? extends Block>, String>();

    public static void init() {
        for (ASMDataTable.ASMData data : DeepMagics.instance.getModBlocks()) {
            try {
                Class<?> asmClass = Class.forName(data.getClassName());

                Class<? extends Block> modBlockClass = asmClass.asSubclass(Block.class);
                String name = modBlockClass.getAnnotation(ModBlock.class).name();
                Class<? extends TileEntity> tileClass = modBlockClass.getAnnotation(ModBlock.class).tileEntity();
                Class<? extends ItemBlock> itemBlockClass = modBlockClass.getAnnotation(ModBlock.class).itemBlock();

                Block modBlock = modBlockClass.newInstance();

                GameRegistry.registerBlock(modBlock, itemBlockClass, name);
                GameRegistry.registerTileEntity(tileClass, Constants.Mod.MODID + ":" + tileClass.getSimpleName());
                classToName.put(modBlockClass, name);
            } catch (Exception e) {
                DeepMagics.instance.getLogger().error(String.format("Unable to register block for class %s", data.getClassName()));
            }
        }
    }

    public static Block getBlock(String name) {
        return GameRegistry.findBlock(Constants.Mod.MODID, name);
    }

    public static Block getBlock(Class<? extends Block> blockClass) {
        return getBlock(classToName.get(blockClass));
    }

    public static String getName(Class<? extends Block> blockClass) {
        return classToName.get(blockClass);
    }

    public static void registerRenders() {
    }
}
