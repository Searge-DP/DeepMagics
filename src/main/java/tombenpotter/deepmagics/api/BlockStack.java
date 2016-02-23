package tombenpotter.deepmagics.api;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameData;

@Getter
@EqualsAndHashCode(exclude = {"state"})
public class BlockStack {

    //This class was originally made for https://github.com/WayofTime/BloodMagic. I just took it, changed it around a bit and updated it.

    private final Block block;
    private final int meta;
    private final IBlockState state;

    public BlockStack(Block block, int meta) {
        this.block = block;
        this.meta = meta;
        this.state = block.getStateFromMeta(meta);
    }

    public BlockStack(Block block) {
        this(block, 0);
    }

    public static BlockStack getStackFromPos(World world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        return new BlockStack(state.getBlock(), state.getBlock().getMetaFromState(state));
    }

    public ItemStack toItemStack() {
        return new ItemStack(block, 1, meta);
    }

    @Override
    public String toString() {
        return GameData.getBlockRegistry().getNameForObject(getBlock()) + ":" + getMeta();
    }
}