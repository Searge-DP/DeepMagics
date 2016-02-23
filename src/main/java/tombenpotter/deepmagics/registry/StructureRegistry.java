package tombenpotter.deepmagics.registry;

import com.google.common.collect.Lists;
import net.minecraft.util.ResourceLocation;
import tombenpotter.deepmagics.DeepMagics;
import tombenpotter.deepmagics.api.world.EnumGenerationType;
import tombenpotter.deepmagics.util.Utils;
import tombenpotter.deepmagics.util.helper.SchematicHelper;
import tombenpotter.deepmagics.world.StructureTemplate;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class StructureRegistry {

    public static final StructureRegistry instance = new StructureRegistry();
    protected List<StructureTemplate> structureTemplateList;

    private StructureRegistry() {
        structureTemplateList = Lists.newArrayList();
    }

    public void registerStructure(StructureTemplate template) {
        structureTemplateList.add(template);
    }

    public StructureTemplate getStructure(int index) {
        if (index < 0 || index >= structureTemplateList.size()) {
            throw new IndexOutOfBoundsException("No structure found at the following index: " + index);
        }
        return structureTemplateList.get(index);
    }

    public int getRegistrySize() {
        return structureTemplateList.size();
    }

    public boolean isRegistryEmpty() {
        return structureTemplateList.isEmpty();
    }

    /**
     * Registers a structure to the CMO structure handler.
     *
     * @param resourceLocation The location of the .modschematic file.
     * @param enumGenerationType   The generation type.
     * @return Whether the structure was successfully registered or not.
     */
    public boolean registerStructure(@Nonnull ResourceLocation resourceLocation, EnumGenerationType enumGenerationType) {
        try {
            return registerStructure(Utils.getFromResource(resourceLocation), resourceLocation.getResourcePath(), enumGenerationType);
        } catch (IOException e) {
            DeepMagics.instance.getLogger().severe(e.getLocalizedMessage());
            return false;
        }
    }

    /**
     * Registers a structure to the CMO structure handler.
     *
     * @param inputStream    The InputStream of the .modschematic file.
     * @param enumGenerationType The generation type.
     * @return Whether the structure was successfully registered or not.
     */
    public boolean registerStructure(@Nonnull InputStream inputStream, String s, EnumGenerationType enumGenerationType) {
        registerStructure(new StructureTemplate(SchematicHelper.INSTANCE.loadModSchematic(inputStream, s), s, enumGenerationType));
        return true;
    }
}