package tombenpotter.deepmagics.api.world;

import net.minecraft.world.World;
import tombenpotter.deepmagics.api.Constants;
import tombenpotter.deepmagics.api.DMBlockPos;

public enum EnumGenerationType {
    SURFACE {
        @Override
        public DMBlockPos getStructurePos(World world, int x, int z) {
            return new DMBlockPos(world.getTopSolidOrLiquidBlock(new DMBlockPos(x, 0, z)));
        }

        @Override
        public DMBlockPos getStructurePos(World world, DMBlockPos DMBlockPos) {
            return getStructurePos(world, DMBlockPos.getX(), DMBlockPos.getZ());
        }
    },
    SEA_LEVEL {
        @Override
        public DMBlockPos getStructurePos(World world, int x, int z) {
            return new DMBlockPos(x, 62, z);
        }

        @Override
        public DMBlockPos getStructurePos(World world, DMBlockPos DMBlockPos) {
            return DMBlockPos.setY(62);
        }
    },
    UNDERGROUND {
        @Override
        public DMBlockPos getStructurePos(World world, int x, int z) {
            return new DMBlockPos(x, 16, z);
        }

        @Override
        public DMBlockPos getStructurePos(World world, DMBlockPos DMBlockPos) {
            return DMBlockPos.setY(16);
        }
    },
    NONE {
        @Override
        public DMBlockPos getStructurePos(World world, int x, int z) {
            return Constants.Misc.NO_POSITION;
        }

        @Override
        public DMBlockPos getStructurePos(World world, DMBlockPos DMBlockPos) {
            return getStructurePos(world, DMBlockPos.getX(), DMBlockPos.getZ());
        }
    };

    public abstract DMBlockPos getStructurePos(World world, int x, int z);

    public abstract DMBlockPos getStructurePos(World world, DMBlockPos DMBlockPos);
}