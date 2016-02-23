package tombenpotter.deepmagics.api;

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
        //Structure related NBT
        public static final String STRUCTURE_X_COORD = "StructureXCoord";
        public static final String STRUCTURE_Y_COORD = "SturctureYCoord";
        public static final String STRUCTURE_Z_COORD = "SturctureZCoord";
        public static final String STRUCTURE_FILE_NAME = "StructureFileName";
        public static final String ENUM_GENERATION_TYPE = "EnumGenerationType";

        //Area related NBT
        public static String START_X = "StartX";
        public static String START_Y = "StartY";
        public static String START_Z = "StartZ";
        public static String END_X = "EndX";
        public static String END_Y = "EndY";
        public static String END_Z = "EndZ";
    }

    public static class Misc {
        public static final DMBlockPos NO_POSITION = new DMBlockPos(0, 0, 0);
    }

    public class Items {
        public static final String SCHEMATIC_GENERATOR = "ItemSchematicGenerator";
    }
}
