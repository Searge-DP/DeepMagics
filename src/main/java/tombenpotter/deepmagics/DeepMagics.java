package tombenpotter.deepmagics;

import lombok.Getter;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import tombenpotter.deepmagics.api.Constants;
import tombenpotter.deepmagics.api.DeepMagicsAPI;
import tombenpotter.deepmagics.api.util.LogHelper;
import tombenpotter.deepmagics.proxy.CommonProxy;
import tombenpotter.deepmagics.registry.ModBlocks;
import tombenpotter.deepmagics.registry.ModItems;
import tombenpotter.deepmagics.registry.ModRecipes;
import tombenpotter.deepmagics.registry.ModStructures;
import tombenpotter.deepmagics.repack.tehnut.lib.annot.Handler;
import tombenpotter.deepmagics.repack.tehnut.lib.annot.ModBlock;
import tombenpotter.deepmagics.repack.tehnut.lib.annot.ModItem;

import java.io.File;
import java.util.Set;

@Mod(modid = Constants.Mod.MODID, name = Constants.Mod.NAME, version = Constants.Mod.VERSION, dependencies = Constants.Mod.DEPEND)
@Getter
public class DeepMagics {

    @Mod.Instance(Constants.Mod.MODID)
    public static DeepMagics instance;

    @SidedProxy(serverSide = "tombenpotter.deepmagics.proxy.CommonProxy", clientSide = "tombenpotter.deepmagics.proxy.ClientProxy")
    public static CommonProxy proxy;

    public static CreativeTabs tabDeepMagics = new CreativeTabs(Constants.Mod.MODID + ".creativeTab") {
        @Override
        public Item getTabIconItem() {
            return Items.item_frame;
        }
    };

    @Getter
    private static File configDirectory;

    private LogHelper logger = new LogHelper(Constants.Mod.MODID);
    private Set<ASMDataTable.ASMData> modItems;
    private Set<ASMDataTable.ASMData> modBlocks;
    private Set<ASMDataTable.ASMData> eventHandlers;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        configDirectory = new File(event.getModConfigurationDirectory(), Constants.Mod.MODID);
        DeepMagicsAPI.setConfigurationDirectory(new File(event.getModConfigurationDirectory(), Constants.Mod.MODID));
        ConfigHandler.init(new File(getConfigDirectory(), Constants.Mod.MODID + ".cfg"));

        modItems = event.getAsmData().getAll(ModItem.class.getCanonicalName());
        modBlocks = event.getAsmData().getAll(ModBlock.class.getCanonicalName());
        eventHandlers = event.getAsmData().getAll(Handler.class.getCanonicalName());

        ModItems.getInstance().init();
        ModBlocks.getInstance().init();
        ModRecipes.getInstance().init();
        ModStructures.getInstance().init();

        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

        proxy.postInit(event);
    }
}
