package tombenpotter.deepmagics.world.structure;

import com.google.common.collect.Sets;
import lombok.Getter;
import tombenpotter.deepmagics.api.schematic.ISchematic;
import tombenpotter.deepmagics.api.structure.EnumGenerationType;
import tombenpotter.deepmagics.api.structure.IStructureTemplate;
import tombenpotter.deepmagics.util.helper.SchematicHelper;

import java.util.Set;

@Getter
public class StructureTemplate implements IStructureTemplate {

    private ISchematic schematic;
    private String fileName;
    private Set<Integer> dimensions;
    private EnumGenerationType enumGenerationType;

    public StructureTemplate(ISchematic schematic, String fileName, EnumGenerationType enumGenerationType, Integer... dimensions) {
        this.schematic = schematic;
        this.fileName = fileName;
        this.enumGenerationType = enumGenerationType;
        this.dimensions = Sets.newHashSet(dimensions);
    }

    public StructureTemplate(ISchematic schematic, String fileName, Integer... dimensions) {
        this(schematic, fileName, EnumGenerationType.NONE, dimensions);
    }

    public StructureTemplate(String fileName, EnumGenerationType enumGenerationType, Integer... dimensions) {
        this(SchematicHelper.INSTANCE.loadModSchematic(fileName, true), fileName, enumGenerationType, dimensions);
    }

    public StructureTemplate(String fileName, Integer... dimensions) {
        this(SchematicHelper.INSTANCE.loadModSchematic(fileName, true), fileName, EnumGenerationType.NONE, dimensions);
    }

    public boolean isDimensionRestricted() {
        return getDimensions() != null && !getDimensions().isEmpty();
    }

    public boolean canSpawnInDimension(int dimension) {
        return !isDimensionRestricted() || getDimensions().contains(dimension);
    }
}
