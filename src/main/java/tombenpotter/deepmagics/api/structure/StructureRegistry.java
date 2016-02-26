package tombenpotter.deepmagics.api.structure;

import com.google.common.collect.Lists;
import net.minecraft.util.ResourceLocation;
import tombenpotter.deepmagics.DeepMagics;
import tombenpotter.deepmagics.util.Utils;
import tombenpotter.deepmagics.util.helper.SchematicHelper;
import tombenpotter.deepmagics.world.structure.StructureTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class StructureRegistry {

    public static final StructureRegistry instance = new StructureRegistry();
    private List<IStructureTemplate> structureTemplateList;

    private StructureRegistry() {
        structureTemplateList = Lists.newArrayList();
    }

    public void registerStructure(IStructureTemplate template) {
        structureTemplateList.add(template);
    }

    public IStructureTemplate getStructure(int index) {
        if (index < 0 || index >= structureTemplateList.size()) {
            throw new IndexOutOfBoundsException("No structure found at the following index: " + index);
        }
        return structureTemplateList.get(index);
    }

    public boolean registerStructure(InputStream inputStream, String fileName, EnumGenerationType enumGenerationType) {
        registerStructure(new StructureTemplate(SchematicHelper.INSTANCE.loadModSchematic(inputStream, fileName), fileName, enumGenerationType));
        return true;
    }

    public boolean registerStructure(ResourceLocation resourceLocation, EnumGenerationType enumGenerationType) {
        try {
            return registerStructure(Utils.getFromResource(resourceLocation), resourceLocation.getResourcePath(), enumGenerationType);
        } catch (IOException e) {
            DeepMagics.instance.getLogger().severe(e.getLocalizedMessage());
            return false;
        }
    }

    public int getRegistrySize() {
        return structureTemplateList.size();
    }

    public boolean isRegistryEmpty() {
        return structureTemplateList.isEmpty();
    }
}