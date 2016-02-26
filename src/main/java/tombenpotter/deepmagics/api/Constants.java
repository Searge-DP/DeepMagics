package tombenpotter.deepmagics.api;

import net.minecraft.world.ChunkCoordIntPair;
import tombenpotter.deepmagics.api.world.DMBlockPos;

import java.util.Locale;

public class Constants {

    public static class Mod {
        public static final String MODID = "DeepMagics";
        public static final String API = "DeepMagics|API";
        public static final String DOMAIN = MODID.toLowerCase(Locale.ENGLISH) + ":";
        public static final String NAME = "The Deep Magics.";
        public static final String VERSION = "@VERSION@";
        public static final String DEPEND = "after:JEI@[2.23.0,)";
    }

    public static class NBT {
        //Misc NBT
        public static final String DIMENSION_ID = "DimensionID";

        //Structure related NBT
        public static final String STRUCTURE_X_COORD = "StructureXCoord";
        public static final String STRUCTURE_Y_COORD = "SturctureYCoord";
        public static final String STRUCTURE_Z_COORD = "SturctureZCoord";
        public static final String STRUCTURE_FILE_NAME = "StructureFileName";
        public static final String ENUM_GENERATION_TYPE = "EnumGenerationType";

        //Area related NBT
        public static final String START_AREA_TAG = "AreaStartTag";
        public static final String END_AREA_TAG = "AreaEndTag";
        public static final String START_X = "StartX";
        public static final String START_Y = "StartY";
        public static final String START_Z = "StartZ";
        public static final String END_X = "EndX";
        public static final String END_Y = "EndY";
        public static final String END_Z = "EndZ";

        //Entropy related NBT
        public static final String ENTROPY_PER_CHUNK = "EntropyPerChunk";
        public static final String CHUNK_COORDS = "EntropyChunkCoords";
        public static final String HAS_STRUCTURE = "HasStructure";
        public static final String TOTAL_STORED_ENERGY = "TotalStoredEnergy";
        public static final String MAX_STOREABLE_ENERGY = "MaxStoreableEnergy";
        public static final String MAX_ACCESSIBLE_ENERGY = "MaxAccessibleEnergy";
        public static final String MAX_ENERGY_BEFORE_RIPPLE = "MaxEnergyBeforeRipple";
        public static final String MAX_SAFELY_STOREABLE_ENERGY = "MaxSafelyStoreableEnergy";
    }

    public static class Misc {
        public static final DMBlockPos NO_POSITION = new DMBlockPos(0, 0, 0);
        public static final ChunkCoordIntPair NO_CHUNK_COORD = new ChunkCoordIntPair(0, 0);
        public static final String BLOCKSTATE_TYPE_NORMAL = "type=normal";
        public static final String WORLD_DATA_ENTROPY = Mod.MODID + "EntropyWorldData";
    }

    public class Items {
        public static final String SCHEMATIC_GENERATOR = "ItemSchematicGenerator";
    }
}
