package com.elytradev.rebar.block;

import com.elytradev.concrete.utilpackets.DisplayToastMessage;
import com.elytradev.rebar.Rebar;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockToaster extends BlockBase {

    public BlockToaster() {
        super(Material.IRON, "toaster");
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote) {
            DisplayToastMessage toast = new DisplayToastMessage(Rebar.NETWORK_CONTEXT,  "This is a message!", "I think it'll work.");
            toast.sendTo(playerIn);
        }

        return true;
    }
}
