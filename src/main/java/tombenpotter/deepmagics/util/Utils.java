package tombenpotter.deepmagics.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Utils {

    @Nonnull
    public static InputStream getFromResource(@Nonnull ResourceLocation resourceLocation) throws IOException {
        String location = "/assets/" + resourceLocation.getResourceDomain() + "/" + resourceLocation.getResourcePath();
        InputStream ret = Utils.class.getResourceAsStream(location);
        if (ret != null) {
            return ret;
        }
        throw new FileNotFoundException(location);
    }

    public static NBTTagCompound checkNBT(NBTTagCompound tagCompound) {
        if (tagCompound == null) {
            return new NBTTagCompound();
        }

        return tagCompound;
    }

    public static NBTTagCompound checkNBT(ItemStack stack) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }

        return stack.getTagCompound();
    }
}
