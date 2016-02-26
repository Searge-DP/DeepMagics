package tombenpotter.deepmagics.api.world;

import lombok.ToString;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import tombenpotter.deepmagics.api.Constants;
import tombenpotter.deepmagics.util.Utils;

@ToString
public class DMBlockPos extends BlockPos {

    private int x;
    private int y;
    private int z;

    public DMBlockPos() {
        super(0, 0, 0);
    }

    public DMBlockPos(int x, int y, int z) {
        super(0, 0, 0);
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public DMBlockPos(double x, double y, double z) {
        super(0, 0, 0);
        this.x = (int) x;
        this.y = (int) y;
        this.z = (int) z;
    }

    public DMBlockPos(Vec3 vec3) {
        super(0, 0, 0);
        this.x = (int) vec3.xCoord;
        this.y = (int) vec3.yCoord;
        this.z = (int) vec3.zCoord;
    }

    public DMBlockPos(Vec3i vec3i) {
        super(0, 0, 0);
        this.x = vec3i.getX();
        this.y = vec3i.getY();
        this.z = vec3i.getZ();
    }

    public DMBlockPos(NBTTagCompound tagCompound) {
        super(0, 0, 0);
        this.x = tagCompound.getInteger(Constants.NBT.STRUCTURE_X_COORD);
        this.y = tagCompound.getInteger(Constants.NBT.STRUCTURE_Y_COORD);
        this.z = tagCompound.getInteger(Constants.NBT.STRUCTURE_Z_COORD);
    }

    public DMBlockPos set(int x, int y, int z) {
        return setX(x).setY(y).setZ(z);
    }

    public DMBlockPos setX(int x) {
        this.x = x;
        return this;
    }

    public DMBlockPos setY(int y) {
        this.y = y;
        return this;
    }

    public DMBlockPos setZ(int z) {
        this.z = z;
        return this;
    }

    @Override
    public DMBlockPos add(int x, int y, int z) {
        return addX(x).addY(y).addZ(z);
    }

    public DMBlockPos addX(int x) {
        this.x += x;
        return this;
    }

    public DMBlockPos addY(int y) {
        this.y += y;
        return this;
    }

    public DMBlockPos addZ(int z) {
        this.z += z;
        return this;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public int getZ() {
        return this.z;
    }

    @Override
    public BlockPos getImmutable() {
        return new BlockPos(this);
    }

    public void writeToNBT(NBTTagCompound tagCompound) {
        tagCompound = Utils.checkNBT(tagCompound);
        tagCompound.setInteger(Constants.NBT.STRUCTURE_X_COORD, getX());
        tagCompound.setInteger(Constants.NBT.STRUCTURE_Y_COORD, getY());
        tagCompound.setInteger(Constants.NBT.STRUCTURE_Z_COORD, getZ());
    }

    public void readFromNBT(NBTTagCompound tagCompound) {
        tagCompound = Utils.checkNBT(tagCompound);
        set(tagCompound.getInteger(Constants.NBT.STRUCTURE_X_COORD), tagCompound.getInteger(Constants.NBT.STRUCTURE_Y_COORD), tagCompound.getInteger(Constants.NBT.STRUCTURE_Z_COORD));
    }
}
