package tombenpotter.deepmagics.registry;

import lombok.Getter;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.registry.GameRegistry;
import tombenpotter.deepmagics.DeepMagics;
import tombenpotter.deepmagics.api.Constants;
import tombenpotter.deepmagics.items.ItemSchematicGenerator;
import tombenpotter.deepmagics.proxy.ClientProxy;
import tombenpotter.deepmagics.repack.tehnut.lib.annot.ModItem;
import tombenpotter.deepmagics.util.IRegistry;

import java.util.HashMap;
import java.util.Map;

public class ModItems implements IRegistry {

    @Getter
    private static ModItems instance = new ModItems();

    private static Map<Class<? extends Item>, String> classToName = new HashMap<Class<? extends Item>, String>();

    private ModItems() {
    }

    @Override
    public void init() {
        for (ASMDataTable.ASMData data : DeepMagics.instance.getModItems()) {
            try {
                Class<?> asmClass = Class.forName(data.getClassName());
                Class<? extends Item> modItemClass = asmClass.asSubclass(Item.class);
                String name = modItemClass.getAnnotation(ModItem.class).name();

                Item modItem = modItemClass.newInstance();

                GameRegistry.registerItem(modItem, name);
                classToName.put(modItemClass, name);
            } catch (Exception e) {
                DeepMagics.instance.getLogger().error(String.format("Unable to register item for class %s", data.getClassName()));
            }
        }
    }

    public void registerRenders() {
        ClientProxy.registerItemModel(ItemSchematicGenerator.class, 0, Constants.Misc.BLOCKSTATE_TYPE_NORMAL);
    }

    public static Item getItem(String name) {
        return GameRegistry.findItem(Constants.Mod.MODID, name);
    }

    public static Item getItem(Class<? extends Item> itemClass) {
        return getItem(classToName.get(itemClass));
    }

    public static String getName(Class<? extends Item> itemClass) {
        return classToName.get(itemClass);
    }
}
