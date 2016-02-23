package tombenpotter.deepmagics.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import tombenpotter.deepmagics.DeepMagics;
import tombenpotter.deepmagics.api.Constants;
import tombenpotter.deepmagics.api.world.Area;
import tombenpotter.deepmagics.repack.tehnut.lib.annot.ModItem;
import tombenpotter.deepmagics.util.Utils;
import tombenpotter.deepmagics.util.helper.SchematicHelper;

@ModItem(name = Constants.Items.SCHEMATIC_GENERATOR)
public class ItemSchematicGenerator extends Item {

    public ItemSchematicGenerator() {
        setUnlocalizedName(Constants.Mod.MODID + ".schematicGenerator");
        setCreativeTab(DeepMagics.tabDeepMagics);
        setMaxStackSize(1);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            NBTTagCompound tag = Utils.checkNBT(stack);

            if (player.isSneaking()) {
                tag.setInteger(Constants.NBT.START_X, pos.getX());
                tag.setInteger(Constants.NBT.START_Y, pos.getY());
                tag.setInteger(Constants.NBT.START_Z, pos.getZ());
            } else {
                tag.setInteger(Constants.NBT.END_X, pos.getX());
                tag.setInteger(Constants.NBT.END_Y, pos.getY());
                tag.setInteger(Constants.NBT.END_Z, pos.getZ());
            }

            player.addChatComponentMessage(new ChatComponentText("Position 1: (X " + tag.getInteger(Constants.NBT.START_X) + " | Y " + tag.getInteger(Constants.NBT.START_Y) + " | Z " + tag.getInteger(Constants.NBT.START_Z) + ")"));
            player.addChatComponentMessage(new ChatComponentText("Position 2: (X " + tag.getInteger(Constants.NBT.END_X) + " | Y " + tag.getInteger(Constants.NBT.END_Y) + " | Z " + tag.getInteger(Constants.NBT.END_Z) + ")"));
        }
        return true;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote && stack.getTagCompound() != null) {
            Area area = new Area(stack.getTagCompound().getInteger(Constants.NBT.START_X), stack.getTagCompound().getInteger(Constants.NBT.START_Y), stack.getTagCompound().getInteger(Constants.NBT.START_Z), stack.getTagCompound().getInteger(Constants.NBT.END_X), stack.getTagCompound().getInteger(Constants.NBT.END_Y), stack.getTagCompound().getInteger(Constants.NBT.END_Z));
            SchematicHelper.INSTANCE.createModSchematic(world, area, (short) 0, "NewSchematic - " + player.getName());
            player.addChatComponentMessage(new ChatComponentText("Created new schematic with a block size of (" + area.getBlockCount() + ")"));
        }
        return stack;
    }
}
