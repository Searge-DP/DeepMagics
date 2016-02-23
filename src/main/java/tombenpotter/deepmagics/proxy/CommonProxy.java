package tombenpotter.deepmagics.proxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import tombenpotter.deepmagics.DeepMagics;
import tombenpotter.deepmagics.repack.tehnut.lib.annot.Handler;
import tombenpotter.deepmagics.repack.tehnut.lib.iface.IProxy;

public class CommonProxy implements IProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        for (ASMDataTable.ASMData data : DeepMagics.instance.getEventHandlers()) {
            try {
                Class<?> asmClass = Class.forName(data.getClassName());
                boolean client = asmClass.getAnnotation(Handler.class).client();

                Object handler = asmClass.newInstance();

                if (!client)
                    MinecraftForge.EVENT_BUS.register(handler);
            } catch (Exception e) {
                DeepMagics.instance.getLogger().error(String.format("Unable to register event handler for class %s", data.getClassName()));
            }
        }

        //NetworkRegistry.INSTANCE.registerGuiHandler(DeepMagics.instance, new GuiHandler());
    }

    @Override
    public void init(FMLInitializationEvent event) {

    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }

    public void registerRenders() {
    }
}
