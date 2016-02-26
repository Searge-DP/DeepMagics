package tombenpotter.deepmagics.api.structure;

import tombenpotter.deepmagics.api.schematic.ISchematic;

import java.util.Set;

public interface IStructureTemplate {

    public ISchematic getSchematic();

    public String getFileName();

    public Set<Integer> getDimensions();

    public EnumGenerationType getEnumGenerationType();

    public boolean isDimensionRestricted();

    public boolean canSpawnInDimension(int dimension);
}
