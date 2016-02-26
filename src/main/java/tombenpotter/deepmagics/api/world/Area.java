package tombenpotter.deepmagics.api.world;

import lombok.Data;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import tombenpotter.deepmagics.api.Constants;

@Data
public class Area {

    private int startX, startY, startZ;
    private int endX, endY, endZ;

    public Area(int startX, int startY, int startZ, int endX, int endY, int endZ) {
        this.startX = startX;
        this.startY = startY;
        this.startZ = startZ;
        this.endX = endX;
        this.endY = endY;
        this.endZ = endZ;
    }

    public Area(BlockPos startPos, BlockPos endPos) {
        this(startPos.getX(), startPos.getY(), startPos.getZ(), endPos.getX(), endPos.getY(), endPos.getZ());
    }

    public static Area getAreaFromNBT(NBTTagCompound tagCompound) {
        if (tagCompound == null) {
            return null;
        }

        NBTTagCompound startTag = tagCompound.getCompoundTag(Constants.NBT.START_AREA_TAG);
        NBTTagCompound endTag = tagCompound.getCompoundTag(Constants.NBT.END_AREA_TAG);

        return new Area(startTag.getInteger(Constants.NBT.START_X), startTag.getInteger(Constants.NBT.START_Y), startTag.getInteger(Constants.NBT.START_Z), endTag.getInteger(Constants.NBT.END_X), endTag.getInteger(Constants.NBT.END_Y), endTag.getInteger(Constants.NBT.END_Z));
    }

    public short getWidth() {
        return (short) (Math.abs(startX - endX) + 1);
    }

    public short getHeight() {
        return (short) (Math.abs(startY - endY) + 1);
    }

    public short getLength() {
        return (short) (Math.abs(startZ - endZ) + 1);
    }

    public int getBlockCount() {
        return getWidth() * getHeight() * getLength();
    }

    public boolean doAreasIntersect(Area otherArea) {
        return !(this.endX < otherArea.startX) && !(otherArea.endX < this.startX)
                && !(this.endY < otherArea.startY) && !(otherArea.endY < this.startY)
                && !(this.endZ < otherArea.startZ) && !(otherArea.endZ < this.startZ);
    }
}
