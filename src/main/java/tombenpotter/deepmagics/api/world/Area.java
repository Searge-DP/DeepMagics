package tombenpotter.deepmagics.api.world;

import lombok.Data;

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
