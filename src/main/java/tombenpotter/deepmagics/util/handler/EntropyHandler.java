package tombenpotter.deepmagics.util.handler;

import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import tombenpotter.deepmagics.repack.tehnut.lib.annot.Handler;
import tombenpotter.deepmagics.util.helper.EntropyHelper;

@Handler
public class EntropyHandler {

    public void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.side == Side.SERVER) {
            EntropyHelper.getInstance().getUniversalEntropy().update(event.world);
        }
    }
}
