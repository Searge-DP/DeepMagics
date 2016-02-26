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

            if (!player.isSneaking()) {
                NBTTagCompound startTag = new NBTTagCompound();
                startTag.setInteger(Constants.NBT.START_X, pos.getX());
                startTag.setInteger(Constants.NBT.START_Y, pos.getY());
                startTag.setInteger(Constants.NBT.START_Z, pos.getZ());
                tag.setTag(Constants.NBT.START_AREA_TAG, startTag);
            } else {
                NBTTagCompound endTag = new NBTTagCompound();
                endTag.setInteger(Constants.NBT.END_X, pos.getX());
                endTag.setInteger(Constants.NBT.END_Y, pos.getY());
                endTag.setInteger(Constants.NBT.END_Z, pos.getZ());
                tag.setTag(Constants.NBT.END_AREA_TAG, endTag);
            }

            NBTTagCompound startTag = tag.getCompoundTag(Constants.NBT.START_AREA_TAG);
            NBTTagCompound endTag = tag.getCompoundTag(Constants.NBT.END_AREA_TAG);
            player.addChatComponentMessage(new ChatComponentText("Position 1: (X " + startTag.getInteger(Constants.NBT.START_X) + " | Y " + startTag.getInteger(Constants.NBT.START_Y) + " | Z " + startTag.getInteger(Constants.NBT.START_Z) + ")"));
            player.addChatComponentMessage(new ChatComponentText("Position 2: (X " + endTag.getInteger(Constants.NBT.END_X) + " | Y " + endTag.getInteger(Constants.NBT.END_Y) + " | Z " + endTag.getInteger(Constants.NBT.END_Z) + ")"));
        }
        return true;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote && stack.getTagCompound() != null) {
            Area area = Area.getAreaFromNBT(stack.getTagCompound());
            SchematicHelper.INSTANCE.createModSchematic(world, area, (short) 0, "NewSchematic - " + player.getName());
            player.addChatComponentMessage(new ChatComponentText("Created new schematic with a block size of (" + area.getBlockCount() + ")"));
        }
        return stack;
    }
}
