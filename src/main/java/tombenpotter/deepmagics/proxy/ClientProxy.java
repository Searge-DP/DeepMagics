package tombenpotter.deepmagics.proxy;

import net.minecraft.block.Block;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import tombenpotter.deepmagics.DeepMagics;
import tombenpotter.deepmagics.api.Constants;
import tombenpotter.deepmagics.items.ItemSchematicGenerator;
import tombenpotter.deepmagics.registry.ModBlocks;
import tombenpotter.deepmagics.registry.ModItems;
import tombenpotter.deepmagics.repack.tehnut.lib.annot.Handler;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);

        for (ASMDataTable.ASMData data : DeepMagics.instance.getEventHandlers()) {
            try {
                Class<?> asmClass = Class.forName(data.getClassName());
                boolean client = asmClass.getAnnotation(Handler.class).client();

                Object handler = asmClass.newInstance();

                if (client)
                    MinecraftForge.EVENT_BUS.register(handler);
            } catch (Exception e) {
                DeepMagics.instance.getLogger().error(String.format("Unable to register event handler for class %s", data.getClassName()));
            }
        }

        registerItemModel(ItemSchematicGenerator.class, 0, "normal");
        registerRenders();
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

    @Override
    public void registerRenders() {
        ModItems.registerRenders();
        ModBlocks.registerRenders();
    }

    public static void registerBlockModel(Class<? extends Block> blockClass, int meta, String variant) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ModBlocks.getBlock(blockClass)), meta, new ModelResourceLocation(new ResourceLocation(Constants.Mod.MODID, ModBlocks.getName(blockClass)), variant));
    }

    public static void registerItemModel(Class<? extends Item> itemClass, int meta, String variant) {
        ModelLoader.setCustomModelResourceLocation(ModItems.getItem(itemClass), meta, new ModelResourceLocation(new ResourceLocation(Constants.Mod.MODID, "item/" + ModItems.getName(itemClass)), variant));
    }

    public static void registerItemVariant(Class<? extends Item> itemClass, String variant) {
        ModelLoader.registerItemVariants(ModItems.getItem(itemClass), new ModelResourceLocation(new ResourceLocation(Constants.Mod.MODID, "item/" + ModItems.getName(itemClass)), variant));
    }
}
